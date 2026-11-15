import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './PatientReviewPage.css';

function PatientReviewPage() {
    const [appointments, setAppointments] = useState([]);
    const patientName = localStorage.getItem('username');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchAppointments = async () => {
            if (!patientName) return;
            try {
                const res = await axios.get(`http://localhost:8080/appointments/by-patient?patientName=${patientName}`);
                const appointmentsWithReviews = await Promise.all(
                    res.data.map(async (appointment) => {
                        try {
                            const reviewsRes = await axios.get(`http://localhost:8080/appointments/${appointment.id}/reviews`);
                            return { ...appointment, reviews: reviewsRes.data };
                        } catch {
                            return { ...appointment, reviews: [] };
                        }
                    })
                );
                setAppointments(appointmentsWithReviews);
            } catch (error) {
                console.error("Error fetching appointments", error);
                setAppointments([]);
            }
        };

        fetchAppointments();
    }, [patientName]);

    const handleBookAppointment = () => {
        navigate('/PatientAppointmentForm'); // update with your route
    };

    const handleGoHome = () => {
        navigate('/PatientDashboard'); // update with your home/dashboard route
    };

    return (
        <div className="review-page">
            <div className="review-page-header">
                <button onClick={handleGoHome} className="home-button">Home</button>
                <h2>Patient Appointment Reviews</h2>
                <button onClick={handleBookAppointment} className="book-button">
                    Book Appointment for New Issue
                </button>
            </div>

            {appointments.length === 0 ? (
                <p>No appointments found.</p>
            ) : (
                <table border="1" cellPadding="10" className="appointment-table">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Doctor</th>
                            <th>Status</th>
                            <th>Visited</th>
                            <th>Rejection Reason</th>
                            <th>Reviews</th>
                        </tr>
                    </thead>
                    <tbody>
                        {appointments.map(app => (
                            <tr key={app.id}>
                                <td>{app.patientName}</td>
                                <td>{app.appointmentDate}</td>
                                <td>{app.appointmentTime}</td>
                                <td>{app.doctorName}</td>
                                <td>{app.status}</td>
                                <td>{app.visited ? 'Yes' : 'No'}</td>
                                <td>{app.rejectionReason || 'N/A'}</td>
                                <td>
                                    {app.reviews && app.reviews.length > 0 ? (
                                        <table className="review-subtable">
                                            <thead>
                                                <tr>
                                                    <th>Week</th>
                                                    <th>Notes</th>
                                                    <th>Date</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {app.reviews.map(review => (
                                                    <tr key={review.id}>
                                                        <td>{review.weekLabel}</td>
                                                        <td>{review.notes}</td>
                                                        <td>{review.date}</td>
                                                    </tr>
                                                ))}
                                            </tbody>
                                        </table>
                                    ) : (
                                        'No reviews'
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default PatientReviewPage;
 