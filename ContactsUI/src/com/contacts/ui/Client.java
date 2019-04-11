package com.contacts.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;
import com.krisztinanmth.phonebook.models.ContactUpdate;
import com.krisztinanmth.phonebook.services.ContactService;
import com.krisztinanmth.phonebook.services.JSONContactService;

public class Client implements ModifyListener {
	
	private static final String JSON_PATH = "/Users/krisztinka/Desktop/contacts.json";
	private ContactService contactService;
	private Set<Contact> contacts;
	
	private Text firstNameText;
	private Text lastNameText;
	private Text phoneNumText;
	private Text dateOfBirthText;
	private Text countryText;
	private Text zipCodeText;
	private Text cityText;
	private Text streetText;
	private Label errorLabel;
	private Color errorColor;
	private Button updateContactBtn;
	
	private Tree contactTree;
	private TreeItem rootNode;
	private MenuItem importItem;
	private Button deleteContactBtn;
	private Button addContactBtn;
	
	public Client() {
		contactService = new JSONContactService(JSON_PATH);
		contacts = contactService.getAllContacts();
		init();
	}

	private void init() {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(GridLayoutFactory.fillDefaults().create());
		shell.setText("phonebook");
		centerWindow(shell);
		
		createMenuBar(shell);
		createWrapperComp(shell);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		if (errorColor != null) {
			errorColor.dispose();
		}
		display.dispose();
	}
	
	private void createMenuBar(Shell shell) {
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileItem.setText("File");
		Menu subMenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(subMenu);
		importItem = new MenuItem(subMenu, SWT.PUSH);
		importItem.setText("Import");
		importItem.addListener(SWT.Selection, e -> importTreeContent());
	}

	private void createWrapperComp(Shell shell) {
		final Composite wrapperComp = new Composite(shell, SWT.NONE);
		wrapperComp.setLayout(new GridLayout(2, false));
		wrapperComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		createTreeSection(wrapperComp);
		createEditorComp(wrapperComp);
	}

	/**
	 * this method only initialises Tree and importTreeContent() method populates it after Import is pushed in Menu/File
	 * @param wrapperComp
	 */
	private void createTreeSection(Composite wrapperComp) {
		final Composite treeComp = new Composite(wrapperComp, SWT.NONE);
		treeComp.setLayout(new GridLayout(1, true));
		treeComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		
		contactTree = new Tree(treeComp, SWT.SINGLE);
		contactTree.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).hint(200, 520).create());
		contactTree.addListener(SWT.Selection, event -> {
			onTreeItemSelection();
		});
		createButtonsComp(treeComp);
	}
	
	private void createButtonsComp(Composite treeComp) {
		final Composite buttonsComp = new Composite(treeComp, SWT.NONE);
		buttonsComp.setLayout(new GridLayout(2, true));
		buttonsComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		addContactBtn = new Button(buttonsComp, SWT.PUSH);
		addContactBtn.setText("+");
		addContactBtn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		addContactBtn.setEnabled(false);
		
		addContactBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				WizardDialog wizDialog = new WizardDialog(Display.getCurrent().getActiveShell(), new CreateContactWizard(c -> createNewContact(c)));
				if (wizDialog != null) {
					wizDialog.open();
				}
			}
		});
		
		deleteContactBtn = new Button(buttonsComp, SWT.PUSH);
		deleteContactBtn.setText("-");
		deleteContactBtn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		deleteContactBtn.setEnabled(false);
		
		deleteContactBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Contact contactToDelete = getSelectedContact();
				if (contactToDelete != null) {
					contactService.deleteContact(contactToDelete.getId());
					refreshTreeAfterDeletingContact();
				}
			}
		});
	}
	
	private void importTreeContent() {
		if (contacts != null) {
			rootNode = new TreeItem(contactTree, SWT.NONE);
			rootNode.setText("CONTACTS:");
			
			for (Contact contact : contacts) {
				final TreeItem contactItemsOfTree = new TreeItem(rootNode, SWT.NONE);
				contactItemsOfTree.setText(contact.getName());
				contactItemsOfTree.setData(contact);
			}
		}
		importItem.setEnabled(false);
		addContactBtn.setEnabled(true);
	}
	
	private void onTreeItemSelection() {
		if (getSelectedContact() == null) {
			return;
		} else {
			Contact selectedContact = getSelectedContact();
			List<Address> addressListOfSelectedContact = selectedContact.getAddress();
			Address addressOfSelectedContact = addressListOfSelectedContact.get(0);

			firstNameText.setText(selectedContact.getFirstName());
			firstNameText.setEnabled(true);
			lastNameText.setText(selectedContact.getLastName());
			lastNameText.setEnabled(true);
			phoneNumText.setText(selectedContact.getPhoneNumber().get(0));
			phoneNumText.setEnabled(true);
			
			dateOfBirthText.setText(selectedContact.getDateOfBirth());
			dateOfBirthText.setEnabled(true);
			
			countryText.setText(addressOfSelectedContact.getCountry());
			countryText.setEnabled(true);
			zipCodeText.setText(addressOfSelectedContact.getZipCode());
			zipCodeText.setEnabled(true);
			cityText.setText(addressOfSelectedContact.getCity());
			cityText.setEnabled(true);
			streetText.setText(addressOfSelectedContact.getStreet());
			streetText.setEnabled(true);
			
			deleteContactBtn.setEnabled(true);
		}
	}
	
	private Contact getSelectedContact() {
		return (Contact) contactTree.getSelection()[0].getData();
	}
	
	private void createNewContact(Contact contact) {
		if (contact != null) {
			contactService.createNewContact(contact);
			redrawTreeWithAddedContact(contact);
		}
	}
	
	private void redrawTreeWithAddedContact(Contact contact) {
			TreeItem newContactTreeItem = new TreeItem(rootNode, SWT.NONE, rootNode.getItemCount());
			newContactTreeItem.setText(contact.getName());
			newContactTreeItem.setData(contact);
	}
	
	private void refreshTreeAfterDeletingContact() {
		Contact deletedContact = getSelectedContact();
		
		for (int i = 0; i < rootNode.getItemCount(); i++) {
			if (rootNode.getItems()[i].getData().equals(deletedContact)) {
				rootNode.getItems()[i].dispose();
			}
		}
		clearEditorFieldsOfDeletedContact();
	}
	
	private void clearEditorFieldsOfDeletedContact() {
		firstNameText.setText("");
		firstNameText.setEnabled(false);
		lastNameText.setText("");
		lastNameText.setEnabled(false);
		phoneNumText.setText("");
		phoneNumText.setEnabled(false);
		dateOfBirthText.setText("");
		dateOfBirthText.setEnabled(false);
		countryText.setText("");
		countryText.setEnabled(false);
		zipCodeText.setText("");
		zipCodeText.setEnabled(false);
		cityText.setText("");
		cityText.setEnabled(false);
		streetText.setText("");
		streetText.setEnabled(false);
		updateContactBtn.setEnabled(false);
		errorLabel.setText("");
	}
	
	private void createEditorComp(Composite wrapperComp) {
		final Composite editorComp = new Composite(wrapperComp, SWT.NONE);
		editorComp.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).create());
		editorComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		GridData labelData = new GridData(SWT.FILL, SWT.FILL, false, false);
		GridData textData = new GridData(SWT.FILL, SWT.FILL, true, false);
		
		// this infoLbl does not have a horizontal-span = 2 on purpose as I need two labels on top and on the left-side(above all the other labels)
		// I needed something with a longer text than any of the other labels, because all the labels have the size of the top label, and that means
		// that a part of the text is not visible on certain labels, it seems that SWT uses the size of the top label, as default
		// so I needed a hack to go around this
		final Label infoLbl = new Label(editorComp, SWT.NONE);
		infoLbl.setLayoutData(labelData);
		infoLbl.setText("PLEASE FILL OUT REQUIRED FIELDS.");
		final Label hiddenLbl = new Label(editorComp, SWT.NONE);
		hiddenLbl.setVisible(false);
		
		final Label firstNameLbl = new Label(editorComp, SWT.NONE);
		firstNameLbl.setLayoutData(labelData);
		firstNameLbl.setText("FIRST NAME (required):");
		
		firstNameText = new Text(editorComp, SWT.NONE);
		firstNameText.setLayoutData(textData);
		firstNameText.setEnabled(false);
		firstNameText.addModifyListener(this);
		
		final Label lastNameLbl = new Label(editorComp, SWT.NONE);
		lastNameLbl.setLayoutData(labelData);
		lastNameLbl.setText("LAST NAME (required):");
		
		lastNameText = new Text(editorComp, SWT.NONE);
		lastNameText.setLayoutData(textData);
		lastNameText.setEnabled(false);
		lastNameText.addModifyListener(this);
		
		final Label phoneNumLbl = new Label(editorComp, SWT.NONE);
		phoneNumLbl.setLayoutData(labelData);
		phoneNumLbl.setText("PHONE NUMBER (required):");
		
		phoneNumText = new Text(editorComp, SWT.NONE);
		phoneNumText.setLayoutData(textData);
		phoneNumText.setEnabled(false);
		phoneNumText.addModifyListener(this);
		
		final Label dateOfBirthLbl = new Label(editorComp, SWT.NONE);
		dateOfBirthLbl.setLayoutData(labelData);
		dateOfBirthLbl.setText("DATE OF BIRTH:");
		
		dateOfBirthText = new Text(editorComp, SWT.NONE);
		dateOfBirthText.setLayoutData(textData);
		dateOfBirthText.setEnabled(false);
		dateOfBirthText.addModifyListener(this);
		
		final Label addressLbl = new Label(editorComp, SWT.NONE);
		GridData addressLblData = new GridData(SWT.LEFT, SWT.FILL, true, false);
		addressLblData.horizontalSpan = 2;
		addressLbl.setLayoutData(addressLblData);
		addressLbl.setText("⬇ ADDRESS ⬇︎");
		
		final Label countryLbl = new Label(editorComp, SWT.NONE);
		countryLbl.setLayoutData(labelData);
		countryLbl.setText("COUNTRY:");
		
		countryText = new Text(editorComp, SWT.NONE);
		countryText.setLayoutData(textData);
		countryText.setEnabled(false);
		
		final Label zipCodeLbl = new Label(editorComp, SWT.NONE);
		zipCodeLbl.setLayoutData(labelData);
		zipCodeLbl.setText("ZIPCODE:");
		
		zipCodeText = new Text(editorComp, SWT.NONE);
		zipCodeText.setLayoutData(textData);
		zipCodeText.setEnabled(false);
		zipCodeText.addModifyListener(this);
		
		final Label cityLbl = new Label(editorComp, SWT.NONE);
		cityLbl.setLayoutData(labelData);
		cityLbl.setText("CITY:");
		
		cityText = new Text(editorComp, SWT.NONE);
		cityText.setLayoutData(textData);
		cityText.setEnabled(false);
		
		final Label streetLbl = new Label(editorComp, SWT.NONE);
		streetLbl.setLayoutData(labelData);
		streetLbl.setText("STREET:");
		
		streetText = new Text(editorComp, SWT.NONE);
		streetText.setLayoutData(textData);
		streetText.setEnabled(false);
		
		errorLabel = new Label(editorComp, SWT.NONE);
		GridData errorLblData = new GridData(SWT.FILL, SWT.FILL, true, false);
		errorLblData.horizontalSpan = 2;
		errorLabel.setLayoutData(errorLblData);
		errorColor = new Color(editorComp.getDisplay(), 139, 0, 0);
		errorLabel.setForeground(errorColor);
		
		updateContactBtn = new Button(editorComp, SWT.PUSH);
		GridData updateContactBtnData = new GridData(SWT.RIGHT, SWT.FILL, false, false);
		updateContactBtnData.horizontalSpan = 2;
		updateContactBtn.setLayoutData(updateContactBtnData);
		updateContactBtn.setText("OK");
		updateContactBtn.setEnabled(false);

		updateContactBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateContact();
			}
		});
	}
	
	@Override
	public void modifyText(ModifyEvent e) {
		validateText();
	}
	
	private void validateText() {
		final List<String> errorMessages = new ArrayList<String>();
		if (firstNameText.getText().length() == 0) {
			errorMessages.add("Please provide first name.");
		} else if (lastNameText.getText().length() == 0) {
			errorMessages.add("Please provide last name.");
		} else if (phoneNumText.getText().length() == 0) {
			errorMessages.add("Please provide phone number.");
		} else if (phoneNumText.getText().length() != 0 && !phoneNumText.getText().matches("^[0-9]+(-?[0-9]+)+$")) {
			errorMessages.add("Phone number can only contain numbers and hyphens.");
		} else if (dateOfBirthText.getText().length() != 0 && !dateOfBirthText.getText().matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")) {
			errorMessages.add("The format for date is yyyy-MM-dd");
		} else if (zipCodeText.getText().length() != 0 && !zipCodeText.getText().matches("[0-9]+")) {
			errorMessages.add("Zipcode can only contain numbers.");
		}
		
		if (errorMessages.size() == 0) {
			errorLabel.setText("");
			updateContactBtn.setEnabled(true);
		} else {
			errorLabel.setText(errorMessages.get(0));
			updateContactBtn.setEnabled(false);
		}
	}
	
	private void updateContact() {
		Contact contact = getSelectedContact();
		ContactUpdate contactUpdate = new ContactUpdate(contact);
		
		String newPhoneNumber = phoneNumText.getText();
		
		List<String> newPhoneNumList = new ArrayList<String>();
		newPhoneNumList.add(newPhoneNumber);
		List<String> oldPhoneNumList = contact.getPhoneNumber();
		
		Address newAddress = new Address();
		if (countryText.getText().length() != 0) {
			newAddress.setCountry(countryText.getText());
		}
		if (zipCodeText.getText().length() != 0) { 
			newAddress.setZipCode(zipCodeText.getText());
		}
		if (cityText.getText().length() != 0) {
			newAddress.setCity(cityText.getText());
		}
		if (streetText.getText().length() != 0) {
			newAddress.setStreet(streetText.getText());
		}
		
		
		List<Address> oldAddList = contact.getAddress();
		List<Address> newAddList = new ArrayList<Address>();
		newAddList.add(newAddress);
		
		
		
		if (!contact.getFirstName().equals(firstNameText.getText())) {
			contactUpdate.setFirstName(firstNameText.getText());
		}
		if (!contact.getLastName().equals(lastNameText.getText())) {
			contactUpdate.setLastName(lastNameText.getText());
		}
		if (!contact.getDateOfBirth().equals(dateOfBirthText.getText())) {
			contactUpdate.setDateOfBirth(dateOfBirthText.getText());
		}
		
		if (!oldPhoneNumList.contains(newPhoneNumber)) {
			for (int i = 0; i < oldPhoneNumList.size(); i++) {
				newPhoneNumList.add(oldPhoneNumList.get(i));
			}
			contactUpdate.setPhoneNumber(newPhoneNumList);
		}
		
		if (!oldAddList.contains(newAddress)) {
			for (int i = 0; i < oldAddList.size(); i++) {
				newAddList.add(oldAddList.get(i));
			}
			contactUpdate.setAddress(newAddList);
		}
		
		contactService.updateContact(contact.getId(), contactUpdate);
		String updatedName = contactUpdate.getFirstName() + " " + contactUpdate.getLastName();
		resetDataOnTreeItems(contact.getId());
		refreshTreeAfterUpdatingContact(updatedName);
	}
	
	private void resetDataOnTreeItems(String id) {
		for (int i = 0; i < contactTree.getItems()[0].getItems().length; i++) {
			Contact originalContact = (Contact) contactTree.getItems()[0].getItem(i).getData();
			if (originalContact.getId().equals(id)) {
				for (Contact c : contacts) {
					if (c.getId().equals(id)) {
						Contact updatedContact = c;
						contactTree.getItems()[0].getItem(i).setData(updatedContact);
					}
				}
			}
		}
	}
	
	private void refreshTreeAfterUpdatingContact(String updatedName) {
		String selectedContactStr = contactTree.getSelection()[0].getText();
		
		for (int i = 0; i < rootNode.getItemCount(); i++) {
			if (rootNode.getItems()[i].getText().equals(selectedContactStr)) {
				rootNode.getItems()[i].setText(updatedName);
			}
		}
	}

	private void centerWindow(Shell shell) {
		Rectangle bds = shell.getDisplay().getBounds();
		Point p = shell.getSize();
		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;
		shell.setBounds(nLeft, nTop, p.x, p.y);
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Client client = new Client();
	}
}
