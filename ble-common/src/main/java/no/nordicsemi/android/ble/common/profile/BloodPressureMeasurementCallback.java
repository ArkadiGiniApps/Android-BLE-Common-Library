package no.nordicsemi.android.ble.common.profile;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Calendar;

@SuppressWarnings("unused")
public interface BloodPressureMeasurementCallback extends BloodPressureMeasurementTypes {

	/**
	 * Callback called when Blood Pressure Measurement packet has been received.
	 *
	 * @param device               target device.
	 * @param systolic             systolic compound of blood pressure measurement.
	 * @param diastolic            diastolic compound of blood pressure measurement.
	 * @param meanArterialPressure mean arterial pressure compound of blood pressure measurement.
	 * @param unit                 measurement unit, one of {@link #UNIT_mmHG} or {@link #UNIT_kPa}.
	 * @param pulseRate            optional pulse rate in beats per minute.
	 * @param userID               optional user ID. Value 255 means 'unknown user'.
	 * @param status               optional measurement status.
	 * @param calendar             optional measurement timestamp.
	 */
	void onBloodPressureMeasurementReceived(final @NonNull BluetoothDevice device, final float systolic, final float diastolic,
											final float meanArterialPressure, final int unit,
											final @Nullable Float pulseRate, final @Nullable Integer userID,
											final @Nullable BPMStatus status, final @Nullable Calendar calendar);
}
