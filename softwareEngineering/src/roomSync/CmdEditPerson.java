package roomSync;

public class CmdEditPerson extends RecordedCommand
{

	private String [] person;
	String attri;

	@Override
	public void execute(String[] cmdParts)
	{
		try
		{
			if(cmdParts.length < 4)
				throw new ExInsufficientCommandArgument();
			
			StudentOffice studentOffice = StudentOffice.getOffice();
			attri= studentOffice.getAttri(new Person(cmdParts[0], cmdParts[1], cmdParts[2], cmdParts[3]));
			studentOffice.removePerson(new Person(cmdParts[0], cmdParts[1], cmdParts[2], cmdParts[3]));		
			studentOffice.addPerson(new Person(cmdParts[0], cmdParts[1], cmdParts[2], cmdParts[3]));
			person = cmdParts;
			addUndoCommand(this);
			clearRedoList();
			System.out.print("\nPerson added Successfully\n");
		}
		catch (ExInsufficientCommandArgument e)
		{
			System.out.print("\n" + e.getMessage() + "\n");
		}
	}

	@Override
	public void undoMe()
	{
		try
		{
			StudentOffice studentOffice = StudentOffice.getOffice();
			studentOffice.removePerson(new Person(person[0], person[1], person[2], person[3]));
			studentOffice.addPerson(new Person(person[0], person[1], person[2], attri));
			addRedoCommand(this);
			System.out.print("\nUndo Successful\n");
		}
		catch (Exception e)
		{
			System.out.println("Error adding person");
		}
		
	}

	@Override
	public void redoMe()
	{
		try
		{
			StudentOffice studentOffice = StudentOffice.getOffice();
			studentOffice.removePerson(new Person(person[0], person[1], person[2], attri));	
			studentOffice.addPerson(new Person(person[0], person[1], person[2], person[3]));
			addUndoCommand(this);
			System.out.print("\nRedo Successful\n");
		}
		catch (Exception e)
		{
			System.out.println("Error adding person");
		}
	}
}
