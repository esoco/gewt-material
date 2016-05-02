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

import gwt.material.design.addins.client.MaterialResourceInjector;

import de.esoco.ewt.component.Button;
import de.esoco.ewt.component.Container;
import de.esoco.ewt.component.Label;
import de.esoco.ewt.component.SplitPanel;
import de.esoco.ewt.component.StackPanel;
import de.esoco.ewt.component.TextArea;
import de.esoco.ewt.component.TextField;
import de.esoco.ewt.impl.gwt.material.MaterialButtonFactory;
import de.esoco.ewt.impl.gwt.material.MaterialLabelFactory;
import de.esoco.ewt.impl.gwt.material.MaterialTextAreaFactory;
import de.esoco.ewt.impl.gwt.material.MaterialTextBoxFactory;
import de.esoco.ewt.impl.gwt.material.layout.MaterialStackPanelLayout;
import de.esoco.ewt.layout.GenericLayout;
import de.esoco.ewt.layout.LayoutMapper;


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
	 * Initializes the library wrapper by registering the necessary factories.
	 */
	public static void init()
	{
		EWT.setLayoutMapper(new MaterialLayoutFactory());

		EWT.registerWidgetFactory(Button.class,
								  new MaterialButtonFactory(),
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

		EWT.registerDefaultWidgetFactories(false);

		MaterialResourceInjector.setDebug(false);
	}

	//~ Inner Classes ----------------------------------------------------------

	/********************************************************************
	 * A factory that maps GEWT layouts to new layouts that create
	 * GwtMaterialDesign panels.
	 *
	 * @author eso
	 */
	static class MaterialLayoutFactory implements LayoutMapper
	{
		//~ Methods ------------------------------------------------------------

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public GenericLayout mapLayout(
			Container	  rContainer,
			GenericLayout rLayout)
		{
			if (rContainer instanceof SplitPanel)
			{
//				rLayout = new MaterialSplitPanelLayout();
			}
			else if (rContainer instanceof StackPanel)
			{
				rLayout = new MaterialStackPanelLayout();
			}

			return rLayout;
		}
	}
}
