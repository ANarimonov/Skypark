package com.anarimonov.skypark.payload;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements Runnable{
    @Value("")
    String token;

    @Override
    public void run() {
        while (true) {
            Thread thread = new Thread(() -> {
                String s = LocalDate.now().toString().substring(8);
                if (s.equals("28")) {
                    databaseBackup();
                }
            });
            thread.start();
            try {
                Thread.sleep(86_400_000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void databaseBackup() {
        Long programmerTGId = 905951214L;
        try {
            String fileName = "Skypark-backup.sql";
            List<String> command = Arrays.asList(
                    "/usr/lib/postgresql/14/bin/pg_dump",
                    "-U", "postgres",
                    "-d", "cazoo_db",
                    "-f", fileName
            );
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.environment().put("PGPASSWORD", ""/* There should be a database password*/);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line1;
            while ((line1 = error.readLine()) != null) {
                System.out.println(line1);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(stringBuilder.toString().getBytes());
                fos.flush();
                fos.close();
                sendDocument(programmerTGId, fileName);
            } else {
                System.out.println("Backup failed. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendDocument(Long programmerTGId, String documentPath) {
        try {
            URL url = new URL("https://api.telegram.org/bot" + token + "/sendDocument");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "multipart/form-data");

            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

            String boundary = "===" + System.currentTimeMillis() + "===";
            writer.write("--" + boundary + "\r\n");
            writer.write("Content-Disposition: form-data; name=\"chat_id\"\r\n\r\n");
            writer.write(programmerTGId + "\r\n");

            writer.write("--" + boundary + "\r\n");
            writer.write("Content-Disposition: form-data; name=\"document\"; filename=\"" + documentPath + "\"\r\n");
            writer.write("Content-Type: application/pdf\r\n\r\n");

            FileInputStream inputStream = new FileInputStream(documentPath);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();

            writer.write("\r\n--" + boundary + "--\r\n");
            writer.close();

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("Response: " + response);

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
