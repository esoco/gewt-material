//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2019 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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

/**
 * The factory for {@link MaterialListBox} widgets.
 *
 * @author eso
 */
public class MaterialListControlFactory
	extends MaterialWidgetFactory<IsListControlWidget> {

	@Override
	public IsListControlWidget createMaterialWidget(Component component,
		StyleData style) {
		return new GewtMaterialListBox();
	}

	/**
	 * A {@link MaterialListBox} subclass that also implements the interface
	 * {@link IsListControlWidget}.
	 *
	 * @author eso
	 */
	static class GewtMaterialListBox extends MaterialListBox
		implements IsListControlWidget {

		/**
		 * Creates a new instance.
		 */
		public GewtMaterialListBox() {
			setNativeBrowserStyle(false);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void addItem(String value) {
			super.addItem(value, false);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setTitle(String text) {
			super.setTitle(text);
		}
	}
}
