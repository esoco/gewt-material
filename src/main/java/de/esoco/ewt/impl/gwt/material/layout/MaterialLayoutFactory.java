package de.esoco.ewt.impl.gwt.material.layout;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.component.SplitPanel;
import de.esoco.ewt.component.StackPanel;
import de.esoco.ewt.layout.GenericLayout;
import de.esoco.ewt.layout.LayoutMapper;
import de.esoco.ewt.layout.MenuLayout;

/********************************************************************
	 * A factory that maps GEWT layouts to new layouts that create
	 * GwtMaterialDesign panels.
	 *
	 * @author eso
	 */
	public class MaterialLayoutFactory implements LayoutMapper
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
			else if (rLayout instanceof MenuLayout)
			{
				rLayout = new MaterialMenuLayout();
			}

			return rLayout;
		}
	}