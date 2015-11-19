package pl.wblacha.texteditor.components;

public class TabItem {

    private String filePath;
    private String tabContent;
    
    public TabItem(String filePath, String tabContent) {
        this.filePath = filePath;
        this.tabContent = tabContent;
    }

    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getTabContent() {
        return tabContent;
    }
    
    public void setTabContent(String tabContent) {
        this.tabContent = tabContent;
    }
}