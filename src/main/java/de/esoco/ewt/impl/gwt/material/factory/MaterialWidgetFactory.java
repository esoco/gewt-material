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
package de.esoco.ewt.impl.gwt.material.factory;

import gwt.material.design.client.base.MaterialWidget;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.impl.gwt.WidgetFactory;
import de.esoco.ewt.style.StyleData;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * The base class for all {@link MaterialWidget} factories.
 *
 * @author eso
 */
public abstract class MaterialWidgetFactory<W extends IsWidget>
	implements WidgetFactory<W> {

	@Override
	@SuppressWarnings("unchecked")
	public final W createWidget(Component component, StyleData style) {
		return createMaterialWidget(component, style);
	}

	/**
	 * Must be implemented by subclasses to create the {@link MaterialWidget}
	 * for the given component.
	 *
	 * @param component The component to create the widget for
	 * @param style     The style of the new widget
	 * @return The new material widget instance
	 */
	protected abstract W createMaterialWidget(Component component,
		StyleData style);
}
