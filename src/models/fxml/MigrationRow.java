package models.fxml;

public class MigrationRow {
    private String dbname;
    private String lastMigration;
    private String actualMigration;
    private String connectionString;

    public MigrationRow(String dbname, String lastMigration, String actualMigration) {
        this.dbname = dbname;
        this.lastMigration = lastMigration;
        this.actualMigration = actualMigration;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getLastMigration() {
        return lastMigration;
    }

    public void setLastMigration(String lastMigration) {
        this.lastMigration = lastMigration;
    }

    public String getActualMigration() {
        return actualMigration;
    }

    public void setActualMigration(String actualMigration) {
        this.actualMigration = actualMigration;
    }
}
