//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2018 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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
package de.esoco.ewt.impl.gwt.material.widget;

import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialTextBox;

import de.esoco.ewt.component.TextControl.IsTextControlWidget;

/**
 * A {@link MaterialTextBox} subclass that also implements the interface
 * {@link IsTextControlWidget}.
 *
 * @author eso
 */
public class GewtMaterialSearch extends MaterialSearch
	implements IsTextControlWidget {

	/**
	 * Creates a new instance.
	 */
	public GewtMaterialSearch() {
		addCloseHandler(e -> setValue("", true));

		getIconSearch().addMouseDownHandler(e -> toggleActive());

		valueBoxBase.addFocusHandler(e -> setActive(true));
		valueBoxBase.addBlurHandler(e -> setActive(false));
	}

	@Override
	public int getCursorPos() {
		return asValueBoxBase().getCursorPos();
	}

	/**
	 * @see MaterialSearch#setActive(boolean)
	 */
	@Override
	public void setActive(boolean active) {
		super.setActive(active);

		MaterialIcon closeIcon = getIconClose();

		closeIcon.setIconColor(null);
		closeIcon.setVisible(active);
		getIconSearch().setIconColor(null);
		setTextColor(null);
	}

	@Override
	public void setVisibleLength(int columns) {
	}

	/**
	 * Toggles the active state of this search field.
	 */
	private void toggleActive() {
		if (isActive()) {
			close();
		} else {
			open();
		}
	}
}
