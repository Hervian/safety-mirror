package com.github.hervian.lambdas;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * Copyright 2016 Anders Granau Høfft
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
 * END OF NOTICE
 * </pre>
 * @author Anders Granau Høfft
 */
@Builder
public class DelegateInvocationResult<RESULT> {

    @Getter
    private boolean oneOrMoreExceptionsThrown;

    @Singular
    private List<FunctionInvocationResult<RESULT>> functionInvocationResults; //TODO: Return immutable copy in the getter?

    public FunctionInvocationResult<RESULT> get(int index){
        return getFunctionInvocationResults().get(index);
    }

    public List<FunctionInvocationResult<RESULT>> getFunctionInvocationResults(){
        return Collections.unmodifiableList(functionInvocationResults);
    }

}
