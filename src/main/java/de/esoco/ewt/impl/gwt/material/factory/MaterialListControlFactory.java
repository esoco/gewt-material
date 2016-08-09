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
package de.esoco.ewt.impl.gwt.material.factory;

import gwt.material.design.client.ui.MaterialListBox;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.component.ListControl.IsListControlWidget;
import de.esoco.ewt.style.StyleData;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;


/********************************************************************
 * The factory for {@link MaterialListBox} widgets.
 *
 * @author eso
 */
public class MaterialListControlFactory
	extends MaterialWidgetFactory<IsListControlWidget>
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public GewtMaterialListBox createMaterialWidget(
		Component rComponent,
		StyleData rStyle)
	{
		return new GewtMaterialListBox();
	}

	//~ Inner Classes ----------------------------------------------------------

	/********************************************************************
	 * A {@link MaterialListBox} subclass that also implements the interface
	 * {@link IsListControlWidget}.
	 *
	 * @author eso
	 */
	static class GewtMaterialListBox extends MaterialListBox
		implements IsListControlWidget
	{
		//~ Instance fields ----------------------------------------------------

		private boolean bInitialized = false;

		//~ Methods ------------------------------------------------------------

		/***************************************
		 * @see #setEnabled(boolean)
		 */
		@Override
		public void onLoad()
		{
			super.onLoad();

			if (!bInitialized)
			{
				bInitialized = true;
			}
		}

		/***************************************
		 * Overridden because of a bug in GWT Material.
		 *
		 * @see https://github.com/GwtMaterialDesign/gwt-material/issues/388
		 * @see MaterialListBox#setEnabled(boolean)
		 */
		@Override
		public void setEnabled(boolean bEnabled)
		{
			super.setEnabled(bEnabled);

			if (bInitialized)
			{
				Widget rWidget = getWidget(0);

				if (rWidget instanceof ListBox)
				{
					initializeMaterial(rWidget.getElement());
				}
			}
		}
	}
}
