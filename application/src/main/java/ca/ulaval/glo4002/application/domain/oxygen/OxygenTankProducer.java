package ca.ulaval.glo4002.application.domain.oxygen;

public class OxygenTankProducer {

    public int calculateNumberOfTanksProduced(int numberOfTanksToProduce, OxygenGrade grade) {
        int bundlesNeeded = (int) Math.ceil((double) numberOfTanksToProduce / grade.getTanksPerBundle());
        return bundlesNeeded * grade.getTanksPerBundle();
    }
}
