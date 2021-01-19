package models.configuration.xml.nodes;

public class SourceNode {
    public String absolutePath;
    public String alias;

    public String getAbsolutePath() {
        return absolutePath;
    }

    public SourceNode setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public SourceNode setAlias(String alias) {
        this.alias = alias;
        return this;
    }
}
