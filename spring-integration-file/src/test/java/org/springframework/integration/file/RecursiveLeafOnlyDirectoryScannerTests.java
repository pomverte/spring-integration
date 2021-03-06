/*
 * Copyright 2002-2015 the original author or authors.
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

package org.springframework.integration.file;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Iwein Fuld
 * @author Gunnar Hillert
 * @author Gary Russell
 */
public class RecursiveLeafOnlyDirectoryScannerTests {

	private File folderThatShouldBeIgnored;

	private File subFolderThatShouldBeIgnored;

	private File topLevelFile;

	private File subLevelFile;

	private File subSubLevelFile;


	@Rule
	public TemporaryFolder recursivePath = new TemporaryFolder() {

		@Override
		public void create() throws IOException {
			super.create();
			folderThatShouldBeIgnored = this.newFolder("shouldBeIgnored");
			subFolderThatShouldBeIgnored = new File(folderThatShouldBeIgnored, "shouldBeIgnored");
			subFolderThatShouldBeIgnored.mkdir();
			topLevelFile = this.newFile("file1");
			subLevelFile = new File(folderThatShouldBeIgnored, "file2");
			subLevelFile.createNewFile();
			subSubLevelFile = new File(subFolderThatShouldBeIgnored, "file2");
			subSubLevelFile.createNewFile();
		}
	};


	@Test
	public void shouldReturnAllFiles() {
		@SuppressWarnings("deprecation")
		List<File> files = new RecursiveLeafOnlyDirectoryScanner().listFiles(recursivePath.getRoot());
		assertEquals(Integer.valueOf(files.size()), Integer.valueOf(3));
		assertThat(files, hasItem(topLevelFile));
		assertThat(files, hasItem(subLevelFile));
		assertThat(files, hasItem(subSubLevelFile));
	}

}
