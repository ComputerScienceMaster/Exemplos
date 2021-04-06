package Components;


public class ComponentsContainer {
    
    //information
    private GeneralInformation generalInformation = new GeneralInformation();
    
    //components
    private Navs nav = new Navs();
    private Style style = new Style();
    private Footers footers = new Footers();
    private Aside asides = new Aside();

    
    
    public Footers getFooters() {
        return footers;
    }

    public void setFooters(Footers footers) {
        this.footers = footers;
    }

    public Navs getNav() {
        return nav;
    }

    public void setNav(Navs nav) {
        this.nav = nav;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public GeneralInformation getGeneralInformation() {
        return generalInformation;
    }

    public void setGeneralInformation(GeneralInformation generalInformation) {
        this.generalInformation = generalInformation;
    }

    public Aside getAsides() {
        return asides;
    }

    public void setAsides(Aside asides) {
        this.asides = asides;
    }
    
    

    
    
}
