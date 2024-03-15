package br.com.ciamed.connectciamed.ConnectCiamed.service.interplayers;

import br.com.ciamed.connectciamed.ConnectCiamed.util.ConnDB;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
@Service
public class InterplayersService {
    public void run(Integer unidade, Integer compra, String cookieValue) {

        try (Connection conn = ConnDB.getConnection()) {

            if (conn != null) {

                String query = """
                        SELECT * FROM DUAL""";

                StringBuilder dadosFormatBuilder = new StringBuilder();

                buildQuery(conn, query, unidade, compra, dadosFormatBuilder);

                // ftp logic and others
                generateAndUploadFile(conn, query, unidade, compra, dadosFormatBuilder.toString());

                // historic insert logic and others
                run2(unidade, compra, dadosFormatBuilder, cookieValue);


            } else {
                System.out.println("Não foi possível obter a conexão com o banco de dados.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try (Connection conn = ConnDB.getConnection()) {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void buildQuery(Connection conn, String query, int unidade, int compra, StringBuilder dadosFormatBuilder) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, compra);
            preparedStatement.setInt(2, unidade);
            preparedStatement.setInt(3, compra);
            preparedStatement.setInt(4, unidade);
            preparedStatement.setInt(5, compra);
            preparedStatement.setInt(6, unidade);
            preparedStatement.setInt(7, compra);
            preparedStatement.setInt(8, unidade);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String dados = resultSet.getString("coluna");
                dadosFormatBuilder.append(dados).append(System.lineSeparator());
                //System.out.println(dados);
            }
        }
    }

    private void generateAndUploadFile(Connection conn, String query, int unidade, int compra, String dadosFormat) throws SQLException {
        String server = "IP";
        int port = 123;
        String username = "123";
        String password = "123";
        String remoteFilePath = "diretorio" + generateFileName();

        uploadFileToFTP(server, port, username, password, dadosFormat.getBytes(), remoteFilePath);
    }

    private String generateFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());
        return "ord_" + timestamp + ".txt";
    }

    private void uploadFileToFTP(String server, int port, String username, String password, byte[] fileData, String remoteFilePath) {
        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData)) {
                boolean uploaded = ftpClient.storeFile(remoteFilePath, inputStream);

                if (uploaded) {
                    //System.out.println("Arquivo enviado com sucesso.");
                } else {
                    System.out.println("Falha ao enviar o arquivo.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void run2(Integer unidade, Integer compra, StringBuilder dadosFormatBuilder, String cookieValue) {


        try (Connection conn = ConnDB.getConnection()) {
            if (conn != null) {
                String query2 = """
                        //insert into ...
                        """;

                insertHistoric(conn, query2, unidade, compra, dadosFormatBuilder, cookieValue);
            } else {
                System.out.println("Não foi possível obter a conexão com o banco de dados.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try (Connection conn = ConnDB.getConnection()) {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertHistoric(Connection conn, String query2, int unidade, int compra, StringBuilder dadosFormatBuilder, String cookieValue) throws SQLException {

        try (PreparedStatement preparedStatement = conn.prepareStatement(query2)) {

            preparedStatement.setString(1, cookieValue);

            preparedStatement.setInt(2, unidade);
            preparedStatement.setInt(3, compra);

            String fileName = generateFileName();
            preparedStatement.setString(4, fileName);

            // Manager date for file_date of cm_interplayers_hist
            Date currentDate = new Date();
            Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
            preparedStatement.setTimestamp(5, currentTimestamp);

            // Manager blob for file_data of cm_interplayers_hist
            ByteArrayInputStream inputStream = new ByteArrayInputStream(dadosFormatBuilder.toString().getBytes());
            preparedStatement.setBlob(6, inputStream);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                //System.out.println("Inserção bem-sucedida!");
            } else {
                System.out.println("A inserção não foi concluída.");
            }
        }
    }



}
