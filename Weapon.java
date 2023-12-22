class Weapon {

    private int str;
    
    public Weapon() {
        str = 10;
    }

    public Weapon(int strength) {
        str = strength;
    }

    public void weaponChances(Player player) {
        int newWeapon = str;
        int rng = (int)(Math.random() * 100) + 1;
        if (rng >= 90) {
            System.out.println("You got a super rare weapon with 200 attack power!");
            newWeapon = 200;
        } else if (rng >= 60) {
            System.out.println("You got a rare weapon with 120 attack power!");
            newWeapon = 120;
        } else if (rng >= 10) {
            System.out.println("You got a common weapon with 60 attack power.");
            newWeapon = 60;
        } else {
            System.out.println("You didn't find anything...");
        }
        if (str < newWeapon) {
            str = newWeapon;
        }
    }


    public String toString() {
        return "" + str;
    }

}