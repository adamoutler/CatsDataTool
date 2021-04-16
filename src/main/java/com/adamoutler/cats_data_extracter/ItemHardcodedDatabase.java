/*
 * To change this license header,
 choose License Headers in Project Properties.
 * To change this template file
 choose Tools | Templates
 * and open the template in the editor.
 */
package com.adamoutler.cats_data_extracter;

import static com.adamoutler.cats_data_extracter.ItemType.ACC;
import static com.adamoutler.cats_data_extracter.ItemType.BODY;
import static com.adamoutler.cats_data_extracter.ItemType.MODIFIER;
import static com.adamoutler.cats_data_extracter.ItemType.WEAPON;
import static com.adamoutler.cats_data_extracter.ItemType.WHEEL;

/**
 *
 * @author adamo
 */
public enum ItemHardcodedDatabase {
    //UltInternalName("Friendly Name", rarity, multiplier, "bonus"),
    UltFlamethrower(WEAPON, "Flamethrower", 3, 1, ""),
    UltTiltedchainsaw(WEAPON, "Rotating Mega Drill G-19", 1, 1, ""),
    UltHearth(WEAPON, "Hearth", 4, 1.16, ""),
    UltDoubleblade(WEAPON, "Double blade", 2, 1, ""),
    UltSchainsaw(WEAPON, "Chainsaw", 1, 1, ""),
    UltBasketcannon(WEAPON, "Basket cannon", 5, 1, "+25% to gadgets"),
    UltDiamondcarp(WEAPON, "Diamond carp", 5, 1, "+10% to bodies"),
    UltIcecreammace(WEAPON, "Ice cream mace", 1, 1, ""),
    UltSkull(WEAPON, "Bone Shaker Skeleton Scream", 5, 1, "+10% to bodies"),
    UltTigerSharkHWtire(WHEEL, "Tiger Shark HW tire", 5, 1, "+5% to weapons"),
    UltSantaslaserbell(WEAPON, "Santa\'s laser bell", 3, 1, ""),
    UltTiltedgun(WEAPON, "Rotating Hyperbolid G27", 1, 1, ""),
    UltBearGun(WEAPON, "Meteor Shower", 4, 1.23, ""),
    UltSantasdoublerocket(WEAPON, "Santa\'s double rocket", 2, 1, ""),
    UltDragonhead(WEAPON, "Dragon head", 2, 0.666613, ""),
    UltLaserheadlight(WEAPON, "Laser headlight", 5, 1, "+10% to bodies"),
    UltBBQ(WEAPON, "BBQ", 5, 1.66669, "+10% to weapons"),
    UltGoldencarp(WEAPON, "Golden carp", 3, 1, ""),
    UltDeathray(WEAPON, "Eye of Death", 3, 0.999975, ""),
    UltSrocket(WEAPON, "Rocket", 2, 1, ""),
    UltJaws(WEAPON, "Jaws", 5, 1, "+10% to bodies"),
    UltTrombonCannon(WEAPON, "Trombone Cannon", 3, 1, ""),
    UltGatlinggun(WEAPON, "Big Boy", 2, 1, ""),
    UltUncleSam(WEAPON, "Uncle Sam", 5, 1, "+10% to weapons"),
    UltMoonbeam(WEAPON, "Swift Laser", 2, 1, ""),
    UltGolemFist(WEAPON, "Golem Fist", 4, 1.25, ""),
    UltBowofthehero(WEAPON, "Spike Strike", 2, .7778, ""),
    UltPigslowscooter(WHEEL, "Pig slow scooter", 1, 1, ""),
    UltBoneshakerHW(BODY, "Bone shaker HW", 5, 1, "+10% to weapons"),
    UltTigerSharkHWbig(WHEEL, "Tiger Shark HW Bigwheel", 5, 1, "+5% to weapons"),
    UltSharkrollerHW(WHEEL, "Shark roller HW", 5, 1, ""),
    UltBonerollerHW(WHEEL, "Bone roller HW", 5, 1, ""),
    UltGolem(BODY, "Golem", 5, 1, ""),
    UltBBoulder(BODY, "Dozer", 1, 1, ""),
    UltEnergyshield(ACC, "Energy shield", 3, 1, ""),
    UltCorsairharpoon(BODY, "Corsair harpoon", 1, 1, ""),
    Ult5alarmHWbig(WHEEL, "5-alarm HW big", 5, 1, "+5% To Bodies"),
    Ult5alarmHW(WHEEL, "5-alarm HW", 5, 1, "+50% to wheels"),
    UltPowerengine3TM(ACC, "Twin Mill Engine", 5, 1, "+10% to bodies"),
    UltBBdrivingknob(WHEEL, "Dozer Drive Knob", 2, 1, ""),
    UltDragon(BODY, "Paws Rover", 3, 1, ""),
    UltSantassleigh(BODY, "Santa\'s sleigh", 3, 1, ""),
    UltStagecoach(BODY, "Stagecoach", 4, 1, ""),
    UltIcedrivingscooter(WHEEL, "Ice Cream Truck driving scooter", 1, 1, ""),
    UltFireengine(BODY, "Fire Hazard", 2, 1, ""),
    UltBBforklift(ACC, "Dozer Forklift", 1, 1, ""),
    UltSharkbiteHW(BODY, "Shark bite HW", 5, 1, "+10% to weapons"),
    UltDiamondpigslowroller(WHEEL, "Diamond pig slow roller", 5, 1, "+10% to weapons"),
    UltBoneknobHW(WHEEL, "Bone knob HW", 5, 1, ""),
    UltRemedy(ACC, "Nanobots Station", 3, 1, ""),
    UltStagecoachtire(WHEEL, "Stagecoach tire", 1, 1, ""),
    UltAntigravscooter2(WHEEL, "Antigrav scooter X70", 2, 1, ""),
    UltPowerengine55A(ACC, "5 Alarm Engine", 5, 1, "+15% to bodies"),
    UltAntigravscooter1(WHEEL, "Antigrav scooter X19", 2, 1, ""),
    UltTigerSharkHW(BODY, "Tiger Shark HW", 5, 1, ""),
    UltPowerengine2SB(ACC, "Shark Bite Engine", 5, 1, "+10% to weapons"),
    UltFirenitro(ACC, "Blasty Nudge", 2, 1, ""),
    UltDragondrivingroller(WHEEL, "Dragon driving roller", 3, 1, ""),
    UltRussianStoveChassis(BODY, "Russian Stove Chassis", 4, 1, ""),
    UltDragonknob(WHEEL, "Dragon knob", 3, 1, ""),
    UltWhip(ACC, "Lucky Horseshoe", 2, 1, ""),
    UltTwinrollerHW(WHEEL, "Twin Mill roller", 5, 1, ""),
    UltPowerengine4TS(ACC, "Tiger Shark Engine", 5, 1, "+15% to weapons"),
    UltSdrivingbigfoot(WHEEL, "Cutter Drive Bigfoot", 1, 1, ""),
    UltSantasgift(ACC, "Santa\'s gift", 3, 1, ""),
    UltGolemLifestone(ACC, "Golem Lifestone", 5, 1, "+10% to bodies"),
    UltSrepulser(ACC, "Cutter Repulse", 1, 1, ""),
    UltTripleTrackroller(WHEEL, "Big Tank Tracks", 4, 1, ""),
    UltDiamond(BODY, "Cloud Body", 1, 1, ""),
    UltPigslowroller(WHEEL, "Pig slow roller", 1, 1, ""),
    UltSneaky(BODY, "Cutter Body", 1, 1, ""),
    UltSantasstickyroller(WHEEL, "Santa\'s sticky roller", 1, 1, ""),
    UltPig(BODY, "Pig", 2, 1, ""),
    UltLotus(ACC, "Lotus", 3, 1, ""),
    UltAntigravroller(WHEEL, "Antigrav roller", 2, 1, ""),
    UltDiamondpigslowscooter(WHEEL, "Diamond pig slow scooter", 5, 1, "+10% to weapons"),
    UltSharkscooterHW(WHEEL, "Shark scooter HW", 5, 1, ""),
    Ult5alarmHWtire(WHEEL, "5-alarm HW tire", 5, 1, "+5% to Bodies"),
    UltIcecreamtruck(BODY, "Glacial Menace", 2, 1, ""),
    UltDragonbackpedal(ACC, "Inverse Thruster", 2, 1, ""),
    UltFiredoubledrivingroller(WHEEL, "Fire Engine Double Roller", 2, 1, ""),
    UltDiamondlotus(ACC, "Diamond Lotus", 5, 1, "+10% to weapons"),
    UltBBguideknob(WHEEL, "Dozer Guide Knob", 1, 1, ""),
    UltIceguidescooter(WHEEL, "Ice Cream Truck Guide Scooter", 1, 1, ""),
    UltMidautumnpyramid(BODY, "Lantern", 3, 1, ""),
    UltSputnik(BODY, "Land Bathyscaphe", 2, 1, ""),
    UltDoubleTrackroller(WHEEL, "Tank Tracks", 3, 1, ""),
    UltChimney(ACC, "Flue", 4, 1, ""),
    UltElixirofimmortality(ACC, "Skull Goblet", 4, 1, ""),
    UltFreezer(ACC, "Frost Sprinkler", 3, 1, ""),
    UltSchoolbus(BODY, "School bus", 4, 1, ""),
    UltDiamondpig(WHEEL, "Diamond pig", 5, 1, "+10% to weapons"),
    UltCorsairdrivingtire(WHEEL, "Corsair driving tire", 1, 1, ""),
    UltTwinknobHW(WHEEL, "Twin knob HW", 4, 1, ""),
    UltFireworks(ACC, "Fireworks", 5, 1, "+10% to bodies"),
    UltStopsign(ACC, "Stop sign", 5, 1, "+10% to weapons"),
    UltBarbedwire(ACC, "Barbed wire", 3, 1, ""),
    UltCorsair(BODY, "Corsair", 2, 1, ""),
    UltTwinmillHW(WHEEL, "Twin mill HW", 5, 1, "+10% to weapons"),
    UltGolemTracks(WHEEL, "Golem Tracks", 5, 1, "+10% to bodies"),
    UltStire(WHEEL, "Cutter Tire", 1, 1, ""),
    UltSantasstickytire(WHEEL, "Santa\'s sticky tire", 2, 1, ""),
    UltGolemRoller(WHEEL, "Stiff Roller", 3, 1, ""),
    UltPowerengine1BS(ACC, "Bone Shaker Engine", 5, 1, "+10% to bodies"),
    UltStagecoachscooter(WHEEL, "Stagecoach scooter", 1, 1, ""),
    UltCorsairguideroller(WHEEL, "Corsair guide roller", 1, 1, ""),
    UltUFO(BODY, "UFO", 4, 1, ""),
    UltFireguideroller(WHEEL, "Fire guide roller", 2, 1, ""),
    UltAntigravknob(WHEEL, "Antigrav knob", 2, 1, ""),
    UltBlazingmace(WEAPON, "Blazing Mace", 3, 1.25, ""),
    UltDoublelaser(WEAPON, "Double Laser", 4, 1, ""),
    UltSalmonCannon(WEAPON, "Salmon Canon", 5, 1, "+10% to bodies"),
    UltBoxing(ACC, "Boxing Glove", 5, 1, "+10% to weapons"),
    UltPopcornLauncher(WEAPON, "Popcorn Launcher", 5, 4.5, "+10% to bodies"),
    UltMagicLamp(ACC, "Magic Lamp", 3, 1, ""),
    UltMetalBeast(BODY, "Metal Beast", 5, 1, "+50% health to wheels"),
    UltMetalHorn(WEAPON, "Horns of Rage", 5, 1, "+10% Damage to weapons"),
    UltMetalLasso(ACC, "Electric Lasso", 4, 1, ""),
    UltTrainDoubleRoller(WHEEL, "Train Double Roller", 4, 1, ""),
    UltTrainGuideKnob(WHEEL, "Train Guide Knob", 3, 1, ""),
    UltDoubleCatcus(WEAPON, "Double Catcus", 5, 1, "+10% to bodies"),
    UltFlowerwhip(WEAPON, "Root Whip", 4, 3, ""),
    UltSunflower(WEAPON, "Sunflower", 4, 0.56455, ""),
    UltFlowerPower(BODY, "Flower Power", 4, 3, ""),
    JackintheBox(MODIFIER, "Jack in the box All Stars modifier", 1, 1, ""),
    UltGyroSpirit(ItemType.WEAPON, "Gyro Spirit", 5, 0.91666666, "+15% to weapons"),
    UltHiddenClaw(ItemType.WEAPON, "Hidden Claw", 4, 1, ""),
    UltBanditKnob(ItemType.WHEEL, "Bandit Knob", 5, 1, "+10% to bodies"),
    UltRogueKnob(ItemType.WHEEL, "Rogue Knob", 5, 1, "+10% to gadgets"),
    UltStingyBandit(ItemType.BODY, "Stingy Bandit", 5, 1, "+10% to weapons"),
    UltStingyKnob(ItemType.WHEEL, "Stingy Knob", 5, 1, "+10% to weapons"),
    UNKNOWN(ItemType.UNKNOWN, "", 0, 1, "UNKNOWN");

    String name = "";
    int rarity = 0;
    private double multiplier = 1;
    private String bonus;
    private ItemType itemType;

    /**
     * an entry in the database
     *
     * @param name name of the item
     * @param rarity the rarity of the item 0-5. 0 unknown. 1 - standard. 2 -
     * polished. 3 - refined. 4 - superior. 5 - outstanding
     * @param multiplier the damage multiplier. Multiply the datavalue by this
     * number to obtain the in-game amount.
     * @param bonus Bonus text displayed on-screen.
     */
    ItemHardcodedDatabase(ItemType itemType, String name, int rarity, double multiplier, String bonus) {
        this.name = name;
        this.rarity = rarity;
        this.multiplier = multiplier;
        this.bonus = bonus;
        this.itemType = itemType;
    }

    public int getRarity() {
        return this.rarity;
    }

    public String getRarityString() {
        switch (rarity) {
            case 0:
                return "unknown";
            case 1:
                return "Standard";
            case 2:
                return "Polished";
            case 3:
                return "Refined";
            case 4:
                return "Superior";
            case 5:
                return "Outstanding";

        }
        return "Unknown";
    }

    public String getString() {
        return name;
    }

    public static ItemHardcodedDatabase reverseLookup(String name) {
        for (ItemHardcodedDatabase rename : ItemHardcodedDatabase.values()) {
            if (rename.getString().equals(name)) {
                return rename;
            }
        }
        return null;
    }

    /**
     * @return the multiplier
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * @return the bonus
     */
    public String getBonus() {
        if (bonus.isEmpty()) {
            return "";
        }
        return bonus;
    }

    /**
     * @return the itemType
     */
    public ItemType getItemType() {
        return itemType;
    }

    /**
     * @param itemType the itemType to set
     */
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
