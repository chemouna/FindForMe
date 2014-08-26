/*
 * Copyright (C) 2013 Simon Vig Therkildsen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mona.android.findforme.tape;

import com.google.gson.Gson;
import com.squareup.tape.FileObjectQueue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import mona.android.findforme.tasks.PhotoUploadTask;
import timber.log.Timber;

public class GsonConverter<T extends PhotoUploadTask> implements FileObjectQueue.Converter<T> {

  private final Gson gson;

  private final Class<T> type;

  public GsonConverter(Gson gson, Class<T> type) {
    this.gson = gson;
    this.type = type;
  }

  @Override
  public T from(byte[] bytes) {
    try {
      Reader reader = new InputStreamReader(new ByteArrayInputStream(bytes));
      return gson.fromJson(reader, type);
    } catch (Throwable e) {
      Timber.e(e, new String(bytes));
    }

    return null; //temp
    //return (T) new DeserializationFailedTask(); //TODO: add this
  }

  @Override
  public void toStream(T object, OutputStream bytes) throws IOException {
    Writer writer = new OutputStreamWriter(bytes);
    gson.toJson(object, type, writer);
    writer.flush();
    writer.close();
  }
}
