package org.example;

public class Dices {
    int firstDice;
    int secondDice;

    public Dices(int firstDice, int secondDice) {
        this.firstDice = firstDice;
        this.secondDice = secondDice;
    }

    public int getFirstDice() {
        return firstDice;
    }

    public void setFirstDice(int firstDice) {
        this.firstDice = firstDice;
    }

    public int getSecondDice() {
        return secondDice;
    }

    public void setSecondDice(int secondDice) {
        this.secondDice = secondDice;
    }

    public int getFrequency() {
        return firstDice == secondDice ? 1 : 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dices dices = (Dices) o;
        return (firstDice == dices.firstDice && secondDice == dices.secondDice) ||
                (firstDice == dices.secondDice && secondDice == dices.firstDice);
    }

    @Override
    public int hashCode() {
        return firstDice + secondDice;
    }

    @Override
    public String toString() {
        return firstDice + " : " + secondDice + " Frequency: " + getFrequency();
    }
}
