/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 *
 * Copyright (C) 2003  The Jmol Development Team
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 *  02111-1307  USA.
 */
package org.openscience.cdk.io;

import org.openscience.cdk.io.setting.*;
import java.util.EventListener;
import java.util.Vector;

/**
 * Allows processing of ReaderSetting quesions which are passed to the user
 * by using the System.out and System.in.
 *
 * @author Egon Willighagen <egonw@sci.kun.nl>
 */
public class TextWriterListener implements WriterListener {

    private int level = 0;
    
    /**
     * 0 = ask no questions
     * 3 = ask all questions
     */
    public TextWriterListener(int level) {
        this.level = level;
    }
    
    public void processWriterSettingQuestion(IOSetting setting){
        if (setting.getLevel() < this.level) {
            System.out.print(setting.getQuestion());
            if (setting instanceof BooleanIOSetting) {
                BooleanIOSetting boolSet = (BooleanIOSetting)setting;
                boolean set = boolSet.isSet();
                if (set) {
                    System.out.print(" [Yn]");
                } else {
                    System.out.print(" [yN]");
                }
            } else if (setting instanceof StringIOSetting) {
                System.out.print(" [" + setting.getSetting() + "]");
            } else if (setting instanceof OptionIOSetting) {
                OptionIOSetting optionSet = (OptionIOSetting)setting;
                Vector settings = optionSet.getOptions();
                for (int i=0; i<settings.size(); i++) {
                    System.out.println();
                    System.out.print((i+1) + ". " + settings.elementAt(i));
                }
            }
            System.out.println();
        }
    };
  
}



