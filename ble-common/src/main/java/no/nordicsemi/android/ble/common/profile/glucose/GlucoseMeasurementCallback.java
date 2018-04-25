package no.nordicsemi.android.ble.common.profile.glucose;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Calendar;

@SuppressWarnings("unused")
public interface GlucoseMeasurementCallback {
	/**
	 * Unit kg/L
	 */
	int UNIT_kg_L = 0;
	/**
	 * Unit mol/L
	 */
	int UNIT_mol_L = 1;

	int TYPE_CAPILLARY_WHOLE_BLOOD = 1;
	int TYPE_CAPILLARY_PLASMA = 2;
	int TYPE_VENOUS_WHOLE_BLOOD = 3;
	int TYPE_VENOUS_PLASMA = 4;
	int TYPE_ARTERIAL_WHOLE_BLOOD = 5;
	int TYPE_ARTERIAL_PLASMA = 6;
	int TYPE_UNDETERMINED_WHOLE_BLOOD = 7;
	int TYPE_UNDETERMINED_PLASMA = 8;
	int TYPE_INTERSTITIAL_FLUID_ISF = 9;
	int TYPE_CONTROL_SOLUTION = 10;

	int SAMPLE_LOCATION_FINGER = 1;
	int SAMPLE_LOCATION_ALTERNATE_SITE_TEST = 2;
	int SAMPLE_LOCATION_EARLOBE = 3;
	int SAMPLE_LOCATION_CONTROL_SOLUTION = 4;
	int SAMPLE_LOCATION_VALUE_NOT_AVAILABLE = 15;

	@SuppressWarnings("WeakerAccess")
	class GlucoseStatus {
		public boolean deviceBatteryLow;
		public boolean sensorMalfunction;
		public boolean sampleSizeInsufficient;
		public boolean stripInsertionError;
		public boolean stripTypeIncorrect;
		public boolean sensorResultLowerThenDeviceCanProcess;
		public boolean sensorResultHigherThenDeviceCanProcess;
		public boolean sensorTemperatureTooHigh;
		public boolean sensorTemperatureTooLow;
		public boolean sensorReadInterrupted;
		public boolean generalDeviceFault;
		public boolean timeFault;
		public int status;

		public GlucoseStatus(final int status) {
			this.status = status;

			deviceBatteryLow = (status & 0x0001) != 0;
			sensorMalfunction = (status & 0x0002) != 0;
			sampleSizeInsufficient = (status & 0x0004) != 0;
			stripInsertionError = (status & 0x0008) != 0;
			stripTypeIncorrect = (status & 0x0010) != 0;
			sensorResultLowerThenDeviceCanProcess = (status & 0x0020) != 0;
			sensorResultHigherThenDeviceCanProcess = (status & 0x0040) != 0;
			sensorTemperatureTooHigh = (status & 0x0080) != 0;
			sensorTemperatureTooLow = (status & 0x0100) != 0;
			sensorReadInterrupted = (status & 0x0200) != 0;
			generalDeviceFault = (status & 0x0400) != 0;
			timeFault = (status & 0x0800) != 0;
		}
	}

	/**
	 * Callback called when Glucose Measurement value was received.
	 *
	 * @param device               target device.
	 * @param glucoseConcentration glucose concentration in the provided unit.
	 * @param unit                 sample unit, one of {@link #UNIT_kg_L} or {@link #UNIT_mol_L}.
	 * @param type                 sample type, see TYPE_* constants.
	 * @param sampleLocation       sample location, see SAMPLE_LOCATION_* constants.
	 * @param sequenceNumber       represents the chronological order of the patient records in the Server
	 *                             measurement database. The initial default value is 0.
	 *                             The Sequence Number is incremented by 1 for each successive
	 *                             Glucose Measurement characteristic value. The maximum value for
	 *                             Sequence Number permitted is 0xFFFF. Assuming a high use of 8 times per day,
	 *                             the maximum value of the Sequence Number would be reached in ~22 years.
	 *                             Since product life expectancy of a Glucose Sensor is ~ 5 years,
	 *                             this value significantly exceeds that expectation.
	 * @param time                 base time with time offset added, if such was present in the packet.
	 */
	void onGlucoseMeasurementReceived(final @NonNull BluetoothDevice device,
									  final float glucoseConcentration, final int unit,
									  final @Nullable Integer type, final @Nullable Integer sampleLocation,
									  final @Nullable GlucoseStatus status,
									  final int sequenceNumber, final @NonNull Calendar time);
}
