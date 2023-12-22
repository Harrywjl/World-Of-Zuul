class Player {

    private int level;
    private int exp;
    private int hp;
    private Weapon weapon;
    private int def;

    public Player() {
        level = 1;
        exp = 0;
        hp = 100;
        weapon = new Weapon();
        def = 0;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public String playerInfo() {
        return "Level = " + level + "\nExperience = " + exp + "/100\nHealth = " + hp + "\nArmor = " + def + "\nWeapon attack power = " + weapon;
    }

    public void newWeapon() {
        weapon.weaponChances(this);
    }

}