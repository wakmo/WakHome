/*******************************************************************************
 * Copyright (c) 2010 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package demo;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 * Created by JBoss Tools
 */
@ManagedBean(name = "user")
@RequestScoped
public class User
{

	private String name;

	private List<String> cars;
	private List<String> colors;

	private String selectedCar;
	private String selectedColor;
	private HtmlSelectOneMenu htmlSelectCars;

	private static final String SELECT_A_CAR = "Select One Car";

	public User()
    {
		cars = new ArrayList<String>();
		colors = new ArrayList<String>();

		colors.add("Red");
		colors.add("Blue");
		colors.add("Orange");
		colors.add("Pink --> O.o");
	}

	public String sayHello()
    {
		if (isNameInCorrect())
        {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Too small", "Can you write it a little bigger?"));
		}

		System.out.println(name);
		return null;
	}

	private boolean isNameInCorrect()
    {
		return name == null || "".equals(name.trim()) || name.length() < 3;
	}

	public String getName()
    {
		return name;
	}

	public void setName(String name)
    {
		this.name = name;
	}

	public void editMyCarsList(AjaxBehaviorEvent event)
    {

		if (htmlSelectCars == null)
        {
			htmlSelectCars = new HtmlSelectOneMenu();
		}

		htmlSelectCars.getChildren().clear();

		UISelectItems items = new UISelectItems();
		items.setValue(getCars());
		htmlSelectCars.getChildren().add(items);
	}

	public List<String> getCars()
    {
		cars.clear();

		cars.add(SELECT_A_CAR);

		if (!isNameInCorrect() && name.length() >= 6)
        {
			cars.add("Ferrari");
			cars.add("Porch");
			cars.add("Beetle");
			cars.add("Opala");
			cars.add("Passat");
			cars.add("Vectra");
			cars.add("Chevet");
			cars.add("Corvet");
		}
        else
        {
			cars.add("Ferrari");
			cars.add("Porch");
			cars.add("Beetle");
			cars.add("Opala");
		}

		return cars;
	}

	public void setCars(List<String> cars)
    {
		this.cars = cars;
	}

	public String getSelectedCar()
    {
		return selectedCar;
	}

	public void setSelectedCar(String selectedCar)
    {
		this.selectedCar = selectedCar;
	}

	public List<String> getColors()
    {
		return colors;
	}

	public void setColors(List<String> colors)
    {
		this.colors = colors;
	}

	public boolean isColorsAlloweToDisplay()
    {
		if (isNameInCorrect())
        {
			return false;
		}

		if (selectedCar == null || selectedCar.trim().equals("") || selectedCar.equals(SELECT_A_CAR))
        {
			return false;
		}

		return true;
	}

	public String getSelectedColor()
    {
		return selectedColor;
	}

	public void setSelectedColor(String selectedColor)
    {
		this.selectedColor = selectedColor;
	}

	public HtmlSelectOneMenu getHtmlSelectCars()
    {
		editMyCarsList(null);

		return htmlSelectCars;
	}

	public void setHtmlSelectCars(HtmlSelectOneMenu htmlSelectCars)
    {
		this.htmlSelectCars = htmlSelectCars;
	}
}