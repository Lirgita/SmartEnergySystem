package org.energy;

import java.util.Scanner;

public class SYSTEM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Energy grid = new Energy();

        System.out.println("WELCOME TO THE SMART ENERGY GRID SIMULATION");


        System.out.print("Enter the number of zones (max 20): ");
        int numZones = scanner.nextInt();
        scanner.nextLine();

        if (numZones > 20) numZones = 20;


        for (int i = 0; i < numZones; i++) {
            System.out.println("Enter details for zone " + (i+1));

            System.out.print("Zone name: ");
            String name = scanner.nextLine();

            System.out.print("Consumption: ");
            double consumption = scanner.nextDouble();

            System.out.print("Production: ");
            double production = scanner.nextDouble();

            System.out.print("Priority (higher number = higher priority): ");
            int priority = scanner.nextInt();

            System.out.print("Is the zone active? (true/false): ");
            boolean active = scanner.nextBoolean();

            System.out.print("Is backup active? (true/false): ");
            boolean backupActive = scanner.nextBoolean();

            scanner.nextLine();

            Zone zone = new Zone(name, consumption, production, priority, active, backupActive);
            grid.addZone(zone);
        }

        System.out.println("\nALL ZONES ADDED. STARTING SIMULATION...\n");


        for (int t = 0; t < 10; t++) {
            System.out.println("=== Simulation round " + (t+1) + " ===");
            grid.simulateRandomFailureActive();
            grid.manageGrid();
            grid.showGridStatus();
            grid.showStatistics();
            System.out.println();
        }


        grid.saveGridState();
        System.out.println("\nSIMULATION ENDED. Grid state saved to file.");
    }
}
