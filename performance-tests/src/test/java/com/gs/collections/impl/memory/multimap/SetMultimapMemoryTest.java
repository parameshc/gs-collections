/*
 * Copyright 2012 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.impl.memory.multimap;

import com.google.common.collect.HashMultimap;
import com.gs.collections.api.block.function.Function0;
import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.impl.memory.MemoryTestBench;
import com.gs.collections.impl.memory.TestDataFactory;
import com.gs.collections.impl.multimap.set.UnifiedSetMultimap;
import org.junit.Test;

public class SetMultimapMemoryTest
{
    @Test
    public void memoryForScaledMultimaps()
    {
        this.memoryForScaledMultimaps(0);
        this.memoryForScaledMultimaps(10);
        this.memoryForScaledMultimaps(100);
        this.memoryForScaledMultimaps(1000);
        this.memoryForScaledMultimaps(10000);
        this.memoryForScaledMultimaps(100000);
        this.memoryForScaledMultimaps(1000000);
    }

    public void memoryForScaledMultimaps(int size)
    {
        MemoryTestBench.on(HashMultimap.class)
                .printContainerMemoryUsage("SetMultiMap", size, new SizedGuavaMultimapFactory(size));
        MemoryTestBench.on(UnifiedSetMultimap.class)
                .printContainerMemoryUsage("SetMultiMap", size, new SizedGscMultimapFactory(size));
    }

    public static class SizedGuavaMultimapFactory implements Function0<HashMultimap<Integer, Integer>>
    {
        private final ImmutableList<Integer> data;

        public SizedGuavaMultimapFactory(int size)
        {
            this.data = TestDataFactory.createImmutableList(size);
        }

        public HashMultimap<Integer, Integer> value()
        {
            final HashMultimap<Integer, Integer> mm = HashMultimap.create();
            this.data.forEach(new Procedure<Integer>()
            {
                public void value(Integer each)
                {
                    for (int j = 0; j < 10; j++)
                    {
                        mm.put(each, Integer.valueOf(j));
                    }
                }
            });
            return mm;
        }
    }

    public static class SizedGscMultimapFactory implements Function0<UnifiedSetMultimap<Integer, Integer>>
    {
        private final ImmutableList<Integer> data;

        public SizedGscMultimapFactory(int size)
        {
            this.data = TestDataFactory.createImmutableList(size);
        }

        public UnifiedSetMultimap<Integer, Integer> value()
        {
            final UnifiedSetMultimap<Integer, Integer> mm = UnifiedSetMultimap.newMultimap();
            this.data.forEach(new Procedure<Integer>()
            {
                public void value(Integer each)
                {
                    for (int j = 0; j < 10; j++)
                    {
                        mm.put(each, Integer.valueOf(j));
                    }
                }
            });
            return mm;
        }
    }
}
