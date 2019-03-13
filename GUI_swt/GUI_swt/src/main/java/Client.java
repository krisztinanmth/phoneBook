import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Client {
	
	public Client() {
		initUI();
	}

	private void initUI() {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		
		shell.setLayout(new GridLayout(1, true));
		shell.setText("phonebook");
		
		final Composite mainComp = new Composite(shell, SWT.NONE);
		final GridData mainData = new GridData(SWT.FILL, SWT.FILL, true, false);
		mainComp.setLayout(new GridLayout(2, false));
		mainComp.setLayoutData(mainData);
		
		createHeaderCom(mainComp);
		
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		display.dispose();
		
	}

	private void createHeaderCom(Composite mainComp) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		Client client = new Client();
	}

}
