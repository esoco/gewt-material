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
package de.esoco.ewt.impl.gwt.material.layout;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.impl.gwt.HasEventHandlingDelay;
import de.esoco.ewt.impl.gwt.material.layout.MaterialContentLayout.GewtMaterialCollapsibleHeader;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.Alignment;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.LayoutProperties.HORIZONTAL_ALIGN;

/**
 * GWT Material implementation for list-style layouts.
 *
 * @author eso
 */
public class MaterialListItemLayout extends AbstractMaterialLayout {

	private boolean isCollapsible;

	private MaterialCollapsibleHeader itemHeader = null;

	private MaterialCollapsibleBody itemBody = null;

	private MaterialCollectionSecondary secondary = null;

	@Override
	public void addWidget(HasWidgets container, Widget widget, StyleData style,
		int index) {
		if (isCollapsible) {
			widget = createCollapsibleItemWidget(widget);
		} else {
			widget = createCollectionItemWidget(widget, style);
		}

		if (widget != null) {
			super.addWidget(container, widget, style, index);
		}
	}

	@Override
	public void clear(HasWidgets container) {
		super.clear(container);

		itemHeader = null;
		itemBody = null;
	}

	@Override
	protected MaterialWidget creatMaterialLayoutContainer(Container container,
		StyleData containerStyle) {
		MaterialWidget hasWidgets;

		isCollapsible =
			container.getParent().getWidget() instanceof MaterialCollapsible;

		if (isCollapsible) {
			hasWidgets = new GewtMaterialCollapsibleItem<Object>();
		} else {
			hasWidgets = new MaterialCollectionItem();
		}

		return hasWidgets;
	}

	/**
	 * Checks whether the given widget needs to be wrapped into a specific
	 * child
	 * widget for a {@link MaterialCollapsible} container.
	 *
	 * @param widget The widget to check
	 * @return Either the original widget, a new wrapper widget or NULL if the
	 * widget has been added to an existing wrapper
	 */
	private Widget createCollapsibleItemWidget(Widget widget) {
		if (itemHeader == null) {
			if (widget instanceof MaterialCollapsibleHeader) {
				itemHeader = (MaterialCollapsibleHeader) widget;
			} else {
				itemHeader = new GewtMaterialCollapsibleHeader(widget);
				widget = itemHeader;
			}
		} else if (itemBody == null) {
			if (widget instanceof MaterialCollapsibleBody) {
				itemBody = (MaterialCollapsibleBody) widget;
			} else {
				itemBody = new MaterialCollapsibleBody(widget);
				widget = itemBody;
			}
		} else {
			itemBody.add(widget);
			widget = null;
		}

		return widget;
	}

	/**
	 * Checks whether the given widget needs to be wrapped into a specific
	 * child
	 * widget for a {@link MaterialCollection} container.
	 *
	 * @param widget The widget to check
	 * @return Either the original widget, a new wrapper widget or NULL if the
	 * widget has been added to an existing wrapper
	 */
	private Widget createCollectionItemWidget(Widget widget, StyleData style) {
		if (style.getProperty(HORIZONTAL_ALIGN, null) == Alignment.END) {
			if (secondary == null) {
				secondary = new MaterialCollectionSecondary();
				secondary.add(widget);
				widget = secondary;
			} else {
				secondary.add(widget);
				widget = null;
			}
		}

		return widget;
	}

	/**
	 * Implements {@link de.esoco.ewt.impl.gwt.HasEventHandlingDelay}.
	 *
	 * @author eso
	 */
	public static class GewtMaterialCollapsibleItem<T>
		extends MaterialCollapsibleItem<T> implements HasEventHandlingDelay {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getEventHandlingDelay() {
			return 300;
		}
	}
}
