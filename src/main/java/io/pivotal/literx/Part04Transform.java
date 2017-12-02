package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to transform values.
 *
 * @author Sebastien Deleuze
 */
public class Part04Transform {

    User capatilizedUser(User user) {
        return new User(user.getUsername().toUpperCase(),
                user.getFirstname().toUpperCase(),
                user.getLastname().toUpperCase());
    }
//========================================================================================

	Mono<User> capitalizeOne(Mono<User> mono) {
        return mono.map(this::capatilizedUser);
    }

//========================================================================================

	Flux<User> capitalizeMany(Flux<User> flux) {
		return flux.map(this::capatilizedUser);
	}

//========================================================================================

	Flux<User> asyncCapitalizeMany(Flux<User> flux) {
		return flux.flatMapSequential(this::asyncCapitalizeUser);
	}

	Mono<User> asyncCapitalizeUser(User u) {
		return Mono.just(new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase()));
	}

}
