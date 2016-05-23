package de.esoco.ewt.impl.gwt.material.widget;

import de.esoco.ewt.component.TextControl.IsTextControlWidget;
import de.esoco.ewt.graphics.Icon;
import de.esoco.ewt.graphics.Image;
import de.esoco.ewt.property.ImageAttribute;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialTextBox;

/********************************************************************
 * A {@link MaterialTextBox} subclass that also implements the interface
 * {@link IsTextControlWidget}.
 *
 * @author eso
 */
public class GewtMaterialTextBox extends MaterialTextBox
	implements IsTextControlWidget, ImageAttribute
{
	//~ Instance fields ----------------------------------------------------

	private Image rImage;

	//~ Methods ------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getCursorPos()
	{
		return asGwtValueBoxBase().getCursorPos();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public Image getImage()
	{
		return rImage;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public String getSelectedText()
	{
		return asGwtValueBoxBase().getSelectedText();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReadOnly()
	{
		return asGwtValueBoxBase().isReadOnly();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setCursorPos(int nPosition)
	{
		asGwtValueBoxBase().setCursorPos(nPosition);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setImage(Image rImage)
	{
		this.rImage = rImage;

		if (rImage instanceof Icon)
		{
			setIconType(IconType.valueOf(((Icon) rImage).getName()));
		}
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setReadOnly(boolean bReadOnly)
	{
		asGwtValueBoxBase().setReadOnly(bReadOnly);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setSelectionRange(int nStart, int nLength)
	{
		asGwtValueBoxBase().setSelectionRange(nStart, nLength);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setVisibleLength(int nColumns)
	{
	}
}