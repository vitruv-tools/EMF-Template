package tools.vitruv.emftemplate.dsl.utils;

import java.util.Optional;

public sealed interface Result<Val, Err> {

    boolean isOk();
    Optional<Val> optionalValue();
    Optional<Err> optionalError();

    record ValResult<Val, Err>(Val value) implements Result<Val, Err> {

        @Override
        public boolean isOk() { return true; }

        @Override
        public Optional<Val> optionalValue() { return Optional.of(value); }

        @Override
        public Optional<Err> optionalError() { return Optional.empty(); }
    
    };
    record ErrResult<Val, Err>(Err error) implements Result<Val, Err> {

        @Override
        public boolean isOk() { return false; }

        @Override
        public Optional<Val> optionalValue() { return Optional.empty(); }

        @Override
        public Optional<Err> optionalError() { return Optional.of(error); }
    
    };

    static <Val, Err> Result<Val, Err> ok(Val value) {
        return new ValResult<Val,Err>(value);
    }
    static <Val, Err> Result<Val, Err> fail(Err error) {
        return new ErrResult<Val,Err>(error);
    }
    
}
