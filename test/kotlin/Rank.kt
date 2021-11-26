import org.jetbrains.kotlinx.dl.api.core.Sequential
import org.jetbrains.kotlinx.dl.api.core.activation.Activations
import org.jetbrains.kotlinx.dl.api.core.initializer.HeNormal
import org.jetbrains.kotlinx.dl.api.core.initializer.Zeros
import org.jetbrains.kotlinx.dl.api.core.layer.core.Dense
import org.jetbrains.kotlinx.dl.api.core.layer.core.Input
import org.jetbrains.kotlinx.dl.api.core.layer.regularization.Dropout
import org.jetbrains.kotlinx.dl.api.core.loss.Losses
import org.jetbrains.kotlinx.dl.api.core.metric.Metrics
import org.jetbrains.kotlinx.dl.api.core.optimizer.Adam
import org.jetbrains.kotlinx.dl.dataset.mnist

private const val SEED = 12L
private const val TEST_BATCH_SIZE = 1000
private const val EPOCHS = 20
private const val TRAINING_BATCH_SIZE = 5000

/**
 * This is a simple model based on Dense layers only.
 */
private val model = Sequential.of(
    Input(128),
    Dense(512, Activations.Relu, kernelInitializer = HeNormal(SEED), biasInitializer = Zeros()),
    Dropout(0.2F),
    Dense(512, Activations.Relu, kernelInitializer = HeNormal(SEED), biasInitializer = Zeros()),
    Dropout(0.2F),
    Dense(256, Activations.Relu, kernelInitializer = HeNormal(SEED), biasInitializer = Zeros()),
    Dropout(0.2F),
    Dense(256, Activations.Relu, kernelInitializer = HeNormal(SEED), biasInitializer = Zeros()),
    Dense(64, Activations.Relu, kernelInitializer = HeNormal(SEED), biasInitializer = Zeros()),
    Dense(3, Activations.Linear, kernelInitializer = HeNormal(SEED), biasInitializer = Zeros())
)

/**
 * This example shows how to do image classification from scratch using [model], without leveraging pre-trained weights or a pre-made model.
 * We demonstrate the workflow on the Mnist classification dataset.
 *
 * It includes:
 * - dataset loading from S3
 * - model compilation
 * - model training
 * - model evaluation
 */
fun denseOnly() {
    val (train, test) = mnist()

    model.use {
        it.compile(optimizer = Adam(), loss = Losses.SOFT_MAX_CROSS_ENTROPY_WITH_LOGITS, metric = Metrics.ACCURACY)

        it.fit(dataset = train, epochs = EPOCHS, batchSize = TRAINING_BATCH_SIZE)

        val accuracy = it.evaluate(dataset = test, batchSize = TEST_BATCH_SIZE).metrics[Metrics.ACCURACY]

        println("Accuracy: $accuracy")
    }
}

/** */
fun main(): Unit = denseOnly()