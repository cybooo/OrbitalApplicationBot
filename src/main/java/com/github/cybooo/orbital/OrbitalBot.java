package com.github.cybooo.orbital;

import com.freya02.botcommands.api.CommandsBuilder;
import com.github.cybooo.orbital.database.MariaDB;
import com.github.cybooo.orbital.events.GuildMemberJoinListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.data.DataObject;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class OrbitalBot {

    private DataObject config;
    private MariaDB mariaDB;
    private JDA jda;

    public OrbitalBot() throws IOException, InterruptedException {

        config = loadConfig();

        mariaDB = new MariaDB(this);
        mariaDB.initialize(
                config.getString("hostname"),
                config.getInt("port"),
                config.getString("username"),
                config.getString("database"),
                config.getString("password")
        );

        jda = JDABuilder.create(config.getString("token"), Arrays.asList(GatewayIntent.values()))
                .addEventListeners(new GuildMemberJoinListener(this))
                .build().awaitReady();


        CommandsBuilder commandsBuilder = CommandsBuilder.newBuilder(485434705903222805L);
        commandsBuilder.build(
                jda,
                "com.github.cybooo.orbital.commands"
        );

        CompletableFuture.runAsync(() -> {
            try (Connection connection = mariaDB.getConnection()) {
                PreparedStatement preparedStatement =
                        connection.prepareStatement("CREATE TABLE IF NOT EXISTS user_data (userid BIGINT, username VARCHAR(256));");
                preparedStatement.executeQuery();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        try {
            new OrbitalBot();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private DataObject loadConfig() {
        File configFile = new File("config.json");
        if (!configFile.exists()) {
            String jarLoc;
            try {
                jarLoc = new File(OrbitalBot.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            InputStream inputStream = OrbitalBot.class.getResourceAsStream(jarLoc + "config.json");
            if (inputStream == null) {
                return DataObject.empty();
            }
            Path path = Paths.get("config.json");
            try {
                Files.copy(inputStream, path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            return DataObject.fromJson(new FileInputStream(configFile));
        } catch (FileNotFoundException ignored) {
        }
        return DataObject.empty();
    }

    public MariaDB getMariaDB() {
        return mariaDB;
    }

    public JDA getJda() {
        return jda;
    }
}