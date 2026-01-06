package org.energy;

import java.io.FileWriter;
import java.io.IOException;

public class Energy {

    private Zone[] zones;
    private int zoneCount;

    public Energy() {
        zones = new Zone[20]; // maximum 20 zones
        zoneCount = 0;
    }

    public void addZone(Zone zone) {
        if (zoneCount < zones.length) {
            zones[zoneCount] = zone;
            zoneCount++;
        } else {
            System.out.println("CANNOT ADD A ZONE");
        }
    }

    public void showGridStatus() {
        System.out.println("GRID STATUS");
        for (int i = 0; i < zoneCount; i++) {
            System.out.println(zones[i]);
        }
    }

    public void checkOverloads() {
        for (int i = 0; i < zoneCount; i++) {
            Zone z = zones[i];
            if (z.isActive() && z.getEnergyBalance() < 0) {
                System.out.println("OVERLOAD IN THIS ZONE! " + z.getZoneName());
                z.activateBackup();
            }
        }
    }

    public void isolateFailedZones() {
        for (int i = 0; i < zoneCount; i++) {
            Zone z = zones[i];
            if (z.isActive() && z.getEnergyBalance() < -100) {
                System.out.println("THE ZONE " + z.getZoneName() + " IS ISOLATED!");
                z.isolate();
            }
        }
    }

    public void manageGrid() {
        checkOverloads();
        redistributeEnergy();
        isolateFailedZones();
    }

    public void redistributeEnergy() {

        for (int i = 0; i < zoneCount; i++) {
            Zone needy = zones[i];

            if (!needy.isActive() || needy.getEnergyBalance() >= 0)
                continue;

            for (int j = 0; j < zoneCount; j++) {
                Zone donor = zones[j];

                if (!donor.isActive())
                    continue;

                if (donor.getEnergyBalance() > 0 && donor.getPriority() > needy.getPriority()) {

                    double transfer = Math.min(
                            donor.getEnergyBalance(),
                            -needy.getEnergyBalance()
                    );

                    donor.giveEnergy(transfer);
                    needy.receiveEnergy(transfer);

                    System.out.println("TRANSFER " + transfer + " FROM " + donor.getZoneName() + " TO " + needy.getZoneName());

                    if (needy.getEnergyBalance() >= 0)
                        break;
                }
            }
        }
    }

    public void simulateRandomFailureActive() {
        // count active zones
        int activeCount = 0;
        for (int i = 0; i < zoneCount; i++) {
            if (zones[i].isActive()) activeCount++;
        }

        if (activeCount == 0) return;

        // pick a random number from 0 to activeCount-1
        int r = (int)(Math.random() * activeCount);

        // find the r-th active zone
        int count = 0;
        for (int i = 0; i < zoneCount; i++) {
            if (zones[i].isActive()) {
                if (count == r) {
                    Zone z = zones[i];
                    System.out.println("Unexpected failure in zone " + z.getZoneName());
                    z.isolate();
                    break;
                }
                count++;
            }
        }
    }

    public void showStatistics() {
        double totalProduction = 0;
        double totalConsumption = 0;
        int activeZones = 0;

        for (int i = 0; i < zoneCount; i++) {
            Zone z = zones[i];
            if (z.isActive()) {
                totalProduction += z.getProduction();
                totalConsumption += z.getConsumption();
                activeZones++;
            }
        }

        System.out.println("STATISTICS");
        System.out.println("Active zones: " + activeZones);
        System.out.println("Total production: " + totalProduction);
        System.out.println("Total consumption: " + totalConsumption);
        System.out.println("Total balance: " + (totalProduction - totalConsumption));
    }

    public void saveGridState() {
        try (FileWriter fw = new FileWriter("grid_state.txt")) {
            for (int i = 0; i < zoneCount; i++) {
                fw.write(zones[i].toString() + "\n");
            }
            System.out.println("Grid state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error while saving the grid state.");
        }
    }
}
