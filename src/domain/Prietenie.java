package domain;

import java.util.Objects;

public class Prietenie extends Entity<Long> {
    private Long idU;
    private Long idP;

    public Prietenie(Long idU, Long idP) {
        this.idU = idU;
        this.idP = idP;
    }

    public Long getIdU() {
        return idU;
    }

    public Long getIdP() {
        return idP;
    }

    public void setIdU(Long idU) {
        this.idU = idU;
    }

    public void setIdP(Long idP) {
        this.idP = idP;
    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "id prietenie=" + id +
                ", idU=" + idU +
                ", idP=" + idP +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prietenie prietenie = (Prietenie) o;
        return Objects.equals(idU, prietenie.idU) && Objects.equals(idP, prietenie.idP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idU, idP);
    }
}

