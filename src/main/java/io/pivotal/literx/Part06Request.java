package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Learn how to control the demand.
 *
 * @author Sebastien Deleuze
 */
public class Part06Request {

	ReactiveRepository<User> repository = new ReactiveUserRepository();

//========================================================================================

	StepVerifier requestAllExpectFour(Flux<User> flux) {
		return StepVerifier.create(flux)
                .thenRequest(Long.MAX_VALUE)
                .expectNextCount(4)
                .expectComplete();
	}

//========================================================================================

	StepVerifier requestOneExpectSkylerThenRequestOneExpectJesse(Flux<User> flux) {
		return StepVerifier.create(flux)
                .thenRequest(1)
                .expectNext(User.SKYLER)
                .thenRequest(1)
                .expectNext(User.JESSE)
                .thenCancel();
	}

//========================================================================================

	Flux<User> fluxWithLog() {
		return repository.findAll().log();
    }

//========================================================================================

	Flux<User> fluxWithDoOnPrintln() {
		return repository.findAll()
                .doOnSubscribe(sub -> System.out.println("Starring:"))
                .doOnNext(user -> System.out.println(user.getFirstname() +  " " + user.getLastname()))
                .doOnComplete(() -> System.out.println("The end!"));
	}

}
