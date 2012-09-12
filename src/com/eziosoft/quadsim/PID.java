package com.eziosoft.quadsim;

public class PID {
	public float	previous_error	= 0f;
	// public float setpoint = 0f;
	// public float process_feedback = 0f;
	public float	integral		= 0f;
	public float	derivative		= 0f;
	public float	error			= 0;

	public float	Kp				= 10f;
	public float	Ki				= 0.0025f;
	public float	Kd				= 900f;

	public float	MaxOutput		= 9999;
	public float	MinOutput		= -9999;

	// public float dt = 10f;

	public PID(float P, float I, float D, float MinOutput, float MaxOutput) {
		Kp = P;
		Ki = I;
		Kd = D;
		this.MaxOutput = MaxOutput;
		this.MinOutput = MinOutput;
	}

	public float Output(float process_feedback, float setpoint, float dt) {

		error = setpoint - process_feedback;
		if (error < MaxOutput || error > MinOutput) {
			integral = integral + (error * dt);
			derivative = (error - previous_error) / dt;
		}

		float output = (Kp * error) + (Ki * integral) + (Kd * derivative);
		previous_error = setpoint - process_feedback;
		if (output > MaxOutput)
			output = MaxOutput;
		if (output < MinOutput)
			output = MinOutput;
		return output;
	}
}
