package ctrl;

public class PanelColor {
    private String rgb;
    private String color;

    public PanelColor(String str) {
        if (str.startsWith("#")) {
            this.rgb = str;
            this.color = toColor(this.rgb);
        } else {
            this.color = str;
            this.rgb = toRGB(this.color);
        }
    }

    public static String toRGB(String strcol) {
        switch (strcol) {
            case "None"  : return "#777777";
            case "Red"   : return "#ff0000";
            case "Green" : return "#00ff00";
            case "White" : return "#ffffff";
            case "Blue"  : return "#8888ff";
            case "Yellow": return "#ffff00";
            default      : return "#000000"; 
        }
    }

    public static String toColor(String strrgb) {
        switch (strrgb) {
            case "#777777" : return "None";
            case "#ff0000" : return "Red";
            case "#00ff00" : return "Green";
            case "#ffffff" : return "White";
            case "#8888ff" : return "Blue";
            case "#ffff00" : return "Yellow";
            default        : return "Black"; 
        }
    }
}