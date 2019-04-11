package com.contacts.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.krisztinanmth.phonebook.models.Address;
import com.krisztinanmth.phonebook.models.Contact;
import com.krisztinanmth.phonebook.models.ContactUpdate;

public class AddNewContactPage extends WizardPage implements ModifyListener {
	
	private Contact initialContact;
	
	private Text firstNameText;  
	private Text lastNameText;
	private Text phoneNumText;
	private Text dateOfBirthText;
	private Text countryText;
	private Text zipCodeText;
	private Text cityText;
	private Text streetText;

	public AddNewContactPage(String pageName) {
		super(pageName);
		setTitle(pageName);
		initialContact = Contact.initialContact();
		setPageComplete(false);
	}
	
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).create());
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createLabelComp(container);
		createTextComp(container);
		
		setControl(container);
	}
	
	private void createLabelComp(Composite container) {
		final Composite labelComp = new Composite(container, SWT.NONE);
		labelComp.setLayout(new GridLayout(1, true));
		labelComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		GridData labelData = new GridData(SWT.FILL, SWT.FILL, true, false);
		
		final Label firstNameLbl = new Label(labelComp, SWT.NONE);
		firstNameLbl.setText("first name (required):");
		firstNameLbl.setLayoutData(labelData);
		
		final Label lastNameLbl = new Label(labelComp, SWT.NONE);
		lastNameLbl.setLayoutData(labelData);
		lastNameLbl.setText("last name (required):");
		
		final Label phoneNumLbl = new Label(labelComp, SWT.NONE);
		phoneNumLbl.setLayoutData(labelData);
		phoneNumLbl.setText("phone number (required):");
		
		final Label dateOfBirthLbl = new Label(labelComp, SWT.NONE);
		dateOfBirthLbl.setLayoutData(labelData);
		dateOfBirthLbl.setText("date of birth: ");
		
		final Label countryLbl = new Label(labelComp, SWT.NONE);
		countryLbl.setLayoutData(labelData);
		countryLbl.setText("country:");
		
		final Label zipCodeLbl = new Label(labelComp, SWT.NONE);
		zipCodeLbl.setLayoutData(labelData);
		zipCodeLbl.setText("zipcode:");
		
		final Label cityLbl = new Label(labelComp, SWT.NONE);
		cityLbl.setLayoutData(labelData);
		cityLbl.setText("city:");
		
		final Label streetLbl = new Label(labelComp, SWT.NONE);
		streetLbl.setLayoutData(labelData);
		streetLbl.setText("street:");
	}
	
	private void createTextComp(Composite container) {
		final Composite textComp = new Composite(container, SWT.NONE);
		textComp.setLayout(new GridLayout(1, true));
		textComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		GridData textData = new GridData(SWT.FILL, SWT.FILL, true, false);
		
		firstNameText = new Text(textComp, SWT.NONE);
		firstNameText.setLayoutData(textData);
		firstNameText.addModifyListener(this);
		
		lastNameText = new Text(textComp, SWT.NONE);
		lastNameText.setLayoutData(textData);
		lastNameText.addModifyListener(this);
		
		phoneNumText = new Text(textComp, SWT.NONE);
		phoneNumText.setLayoutData(textData);
		phoneNumText.addModifyListener(this);
		
		dateOfBirthText = new Text(textComp, SWT.NONE);
		dateOfBirthText.setLayoutData(textData);
		dateOfBirthText.addModifyListener(this);
		
		countryText = new Text(textComp, SWT.NONE);
		countryText.setLayoutData(textData);
		countryText.addModifyListener(this);
		
		zipCodeText = new Text(textComp, SWT.NONE);
		zipCodeText.setLayoutData(textData);
		zipCodeText.addModifyListener(this);
		
		cityText = new Text(textComp, SWT.NONE);
		cityText.setLayoutData(textData);
		cityText.addModifyListener(this);
		
		streetText = new Text(textComp, SWT.NONE);
		streetText.setLayoutData(textData);
		streetText.addModifyListener(this);
	}
	
	@Override
	public void modifyText(ModifyEvent e) {
		validateText();
		setFields();
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
			setErrorMessage(null);
			setPageComplete(true);
			
		} else {
			setErrorMessage(errorMessages.get(0));
			setPageComplete(false);
		}
	}
	
	private void setFields() {
		
		initialContact = initialContact.withFirstName(firstNameText.getText());
		initialContact = initialContact.withLastName(lastNameText.getText());
		
		List<String> initialPhoneNumList = new ArrayList<String>();
		initialPhoneNumList.add(phoneNumText.getText());
		initialContact = initialContact.withPhoneNumber(initialPhoneNumList);
		
		if (!dateOfBirthText.getText().equals(initialContact.getDateOfBirth())) {
			initialContact = initialContact.withDateOfBirth(dateOfBirthText.getText());
		}
		
		List<Address> initialAddressList = new ArrayList<Address>();
		Address initialAddress = new Address();
		
		if (countryText.getText().length() != 0) {
			initialAddress.setCountry(countryText.getText());
		}
		if (zipCodeText.getText().length() != 0) {
			initialAddress.setZipCode(zipCodeText.getText());
		}
		if (cityText.getText().length() != 0) {
			initialAddress.setCity(cityText.getText());
		}
		if (streetText.getText().length() != 0) {
			initialAddress.setStreet(streetText.getText());
		}
		initialAddressList.add(initialAddress);
		initialContact = initialContact.withAddress(initialAddressList);
	}
	
	protected Contact getContact() {
		return initialContact;
	}
}
