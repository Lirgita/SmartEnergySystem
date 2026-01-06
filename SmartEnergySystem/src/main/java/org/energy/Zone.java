package org.energy;

public class Zone {
    private String zoneName;
    private double consumption;
    private double production;
    private int priority;
    private boolean active;
    private boolean backupActive;

    public Zone() {
    }

    public Zone(String zoneName, double consumption, double production, int priority, boolean active, boolean backupActive) {
        this.zoneName = zoneName;
        this.consumption = consumption;
        this.production = production;
        this.priority = priority;
        this.active = active;
        this.backupActive = backupActive;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public double getProduction() {
        return production;
    }

    public void setProduction(double production) {
        this.production = production;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBackupActive() {
        return backupActive;
    }

    public void setBackupActive(boolean backupActive) {
        this.backupActive = backupActive;
    }
    public double getEnergyBalance() {
        return production - consumption;
    }
    public void activateBackup() {
        if (!backupActive) {
            backupActive = true;
            production += 50;
        }
    }
    public void isolate() {
        active = false;
        production = 0;
    }
    public void giveEnergy(double amount) {
        production -= amount;
    }

    public void receiveEnergy(double amount) {
        production += amount;
    }

    @Override
    public String toString() {
        return "Zona: " + zoneName +
                " | Konsum: " + consumption +
                " | Prodhim: " + production +
                " | Bilanc: " + getEnergyBalance() +
                " | Prioritet: " + priority +
                " | Aktiv: " + active +
                " | Backup: " + backupActive;
    }

}
