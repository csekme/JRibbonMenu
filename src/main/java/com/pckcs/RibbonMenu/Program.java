/**
 * Copyright 2020-2022 Csekme Krisztián
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pckcs.RibbonMenu;

import com.formdev.flatlaf.FlatIntelliJLaf;

import java.awt.EventQueue;

/**
 * The Class Program is a simple demonstration of our ribbon.
 */
public class Program {

	/**
   * The main method.
   *
   * @param args
   *          the arguments
   */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlatIntelliJLaf.setup();
					//FlatDarculaLaf.setup();
				 	MainWindow frame = new MainWindow();
			    frame.setLocationRelativeTo(null);
				 	frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
