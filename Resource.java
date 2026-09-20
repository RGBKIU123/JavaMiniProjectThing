public abstract class Resource
{
    private int resourceId;
    private String name;
    private String description;
    private String condition;
    private Student owner;
    private boolean available;

    public abstract String getResourceType();

    public abstract void displayDetails();
}