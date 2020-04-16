package se.giron.moviecenter.model.enums;

public enum RoleEnum {
    ACTOR("Skådespelare"),
    DIRECTOR("Regissör"),
    PRODUCER("Producent"),
    MUSIC("Musik"),
    WRITER("Manus"),
    CASTING("Rollbesättare"),
    EDITOR("Redigerare"),
    CINEMATOGRAPHY("Cinematografi"),
    SOUND("Ljud"),
    ART("Konst"),
    MISC("Övrigt");

    private String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
