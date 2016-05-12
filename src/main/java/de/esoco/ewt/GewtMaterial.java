//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2016 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//	  http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
package de.esoco.ewt;

import de.esoco.ewt.component.Button;
import de.esoco.ewt.component.CheckBox;
import de.esoco.ewt.component.Label;
import de.esoco.ewt.component.List;
import de.esoco.ewt.component.ListBox;
import de.esoco.ewt.component.RadioButton;
import de.esoco.ewt.component.TextArea;
import de.esoco.ewt.component.TextField;
import de.esoco.ewt.impl.gwt.material.factory.MaterialButtonFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialCheckBoxFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialLabelFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialListControlFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialRadioButtonFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialTextAreaFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialTextBoxFactory;
import de.esoco.ewt.impl.gwt.material.layout.MaterialLayoutFactory;


/********************************************************************
 * The central management and factory class for the GEWT wrapper of the
 * GwtMaterialDesign library.
 *
 * @author eso
 */
public class GewtMaterial
{
	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a new instance.
	 */
	private GewtMaterial()
	{
	}

	//~ Static methods ---------------------------------------------------------

	/***************************************
	 * Returns the current JQuery version.
	 *
	 * @return The JQuery version string
	 */
	public static native String getJQueryVersion() /*-{
		var jQuery = $wnd['jQuery'];
		if (typeof jQuery != 'undefined') {
			return jQuery.fn.jquery;
		}
		else {
			return 'undefined';
		}
	}-*/;

	/***************************************
	 * Initializes the library wrapper by registering the necessary factories.
	 */
	public static void init()
	{
		MaterialLayoutFactory aLayoutFactory = new MaterialLayoutFactory();

		EWT.setLayoutFactory(aLayoutFactory);
		EWT.setLayoutMapper(aLayoutFactory);

		initWidgetFactories();

		EWT.registerDefaultWidgetFactories(false);
	}

	/***************************************
	 * Initializes the GwtMaterialDesign-specific widget factories.
	 */
	private static void initWidgetFactories()
	{
		MaterialListControlFactory aListControlFactory =
			new MaterialListControlFactory();

		EWT.registerWidgetFactory(List.class, aListControlFactory, true);
		EWT.registerWidgetFactory(ListBox.class, aListControlFactory, true);

		EWT.registerWidgetFactory(Button.class,
								  new MaterialButtonFactory<>(),
								  true);
		EWT.registerWidgetFactory(CheckBox.class,
								  new MaterialCheckBoxFactory<>(),
								  true);
		EWT.registerWidgetFactory(RadioButton.class,
								  new MaterialRadioButtonFactory(),
								  true);
		EWT.registerWidgetFactory(Label.class,
								  new MaterialLabelFactory<>(),
								  true);
		EWT.registerWidgetFactory(TextArea.class,
								  new MaterialTextAreaFactory(),
								  true);
		EWT.registerWidgetFactory(TextField.class,
								  new MaterialTextBoxFactory(),
								  true);
	}
}
