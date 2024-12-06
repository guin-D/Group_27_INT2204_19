package LibraryManagement.commandline;

/**
 * The IOOperation interface defines a contract for operations that can be performed by a user in the application.
 * Any class that implements this interface must define the 'oper' method which will execute specific actions for the user.
 */
public interface IOOperation {

    /**
     * Executes the operation for the given user.
     *
     * @param user The user who is performing the operation.
     */
    void oper(User user);
}
