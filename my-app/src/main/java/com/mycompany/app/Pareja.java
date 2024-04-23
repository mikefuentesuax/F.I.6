

public class Pareja<T, U> {
    private T primero;
    private U segundo;

    public Pareja(T primero, U segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    public T getPrimero() {
        return primero;
    }

    public U getSegundo() {
        return segundo;
    }

    public void setPrimero(T primero) {
        this.primero = primero;
    }

    public void setSegundo(U segundo) {
        this.segundo = segundo;
    }

    @Override
    public String toString() {
        return "(" + primero.toString() + ", " + segundo.toString() + ")";
    }
}