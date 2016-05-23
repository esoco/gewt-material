package de.esoco.ewt.impl.gwt.material.widget;

import com.google.gwt.user.client.ui.HasText;

import gwt.material.design.client.ui.MaterialTitle;

/********************************************************************
 * A {@link MaterialTitle} subclass that also implements {@link HasText}.
 *
 * @author eso
 */
public class GewtMaterialTitle extends MaterialTitle
	implements HasText
{
	//~ Methods ------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public String getText()
	{
		return super.getTitle();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setText(String sText)
	{
		super.setTitle(sText);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setTitle(String sTooltip)
	{
		super.setTooltip(sTooltip);
	}
}