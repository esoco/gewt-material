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

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import de.esoco.ewt.EWT;
import de.esoco.ewt.component.ChildView.ChildViewFactory;
import de.esoco.ewt.component.ChildView.IsChildViewWidget;
import de.esoco.ewt.component.View;
import de.esoco.ewt.style.ViewStyle;
import de.esoco.ewt.style.ViewStyle.Flag;
import gwt.material.design.client.constants.DialogType;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialTitle;

/**
 * A child view factory that creates instances of material dialogs.
 *
 * @author eso
 */
public class MaterialChildViewFactory extends ChildViewFactory {

	@Override
	public IsChildViewWidget createChildViewWidget(View parent,
		ViewStyle style) {
		if (style.hasFlag(ViewStyle.Flag.MODAL)) {
			return createViewWidget(parent, style);
		} else {
			return super.createChildViewWidget(parent, style);
		}
	}

	/**
	 * Creates the actual view widget.
	 *
	 * @param parent The parent view
	 * @param style  The view style
	 * @return The view widget
	 */
	private IsChildViewWidget createViewWidget(View parent, ViewStyle style) {
		GewtMaterialDialog modal = new GewtMaterialDialog(style);
		Widget parentWidget = parent.getWidget();

		if (parentWidget instanceof HasWidgets) {
			((HasWidgets) parentWidget).add(modal);
		} else {
			throw new IllegalStateException("Unsupported parent widget type");
		}

		return modal;
	}

	/**
	 * A subclass of material dialogs that also implements the child view
	 * interface.
	 *
	 * @author eso
	 */
	public static class GewtMaterialDialog extends MaterialDialog
		implements IsChildViewWidget {

		private MaterialTitle title = null;

		/**
		 * Creates a new instance.
		 *
		 * @param style The view style
		 */
		public GewtMaterialDialog(ViewStyle style) {
			setInDuration(500);
			setOutDuration(500);
			setDismissible(style.hasFlag(Flag.AUTO_HIDE));

			if (style.hasFlag(Flag.BOTTOM)) {
				setType(DialogType.BOTTOM_SHEET);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void hide() {
			close();

			if (getParent() instanceof HasWidgets) {
				((HasWidgets) getParent()).remove(this);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isShown() {
			return isAttached();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setViewTitle(String text) {
			if (title == null) {
				title = new MaterialTitle(text);
				insert(title, 0);

				title.addStyleName(EWT.CSS.ewtDialogTitle());
			} else {
				title.setTitle(text);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void show() {
			open();
		}
	}
}
