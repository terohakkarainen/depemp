package fi.thakki.depemp.type;

public final class DepartmentId {

    private final long myValue;

    private DepartmentId(long value) {
       myValue = value;
    }

    public static DepartmentId valueOf(long id) {
        return new DepartmentId(id);
    }

    public long longValue() {
       return myValue;
    }
}
