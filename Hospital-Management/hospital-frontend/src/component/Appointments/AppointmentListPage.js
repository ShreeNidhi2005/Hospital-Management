import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { FaSearch } from "react-icons/fa";
import { IoPlaySkipForward, IoPlaySkipBack } from "react-icons/io5";
import './AppointmentListPage.css';

const AppointmentListPage = () => {
  const [appointments, setAppointments] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [updateId, setUpdateId] = useState('');
  const [updateDate, setUpdateDate] = useState('');
  const [updateTime, setUpdateTime] = useState('');
  const [deleteId, setDeleteId] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [reviewInputs, setReviewInputs] = useState({});
  const rowsPerPage = 8;

  useEffect(() => {
    fetchAppointments();
  }, []);

  const fetchAppointments = async () => {
    try {
      const response = await axios.get('https://hospital-management-backend-a52a.onrender.com/appointments/all');
      setAppointments(Array.isArray(response.data) ? response.data : []);
      setCurrentPage(1);
    } catch (error) {
      console.error('Error fetching appointments:', error);
      setAppointments([]);
    }
  };

  const handleSearch = async () => {
    if (searchTerm.trim() === '') {
      fetchAppointments();
      return;
    }

    const isNumeric = /^\d+$/.test(searchTerm.trim());

    try {
      const response = isNumeric
        ? await axios.get(`https://hospital-management-backend-a52a.onrender.com/appointments/${searchTerm}`)
        : await axios.get(`https://hospital-management-backend-a52a.onrender.com/appointments/by-patient?patientName=${searchTerm}`);

      const results = isNumeric ? [response.data] : response.data;
      setAppointments(results);
      setCurrentPage(1);
    } catch (err) {
      console.error('Search error:', err);
      setAppointments([]);
    }
  };

  const handleUpdateTop = async () => {
    if (!updateId || !updateDate || !updateTime) return alert('Please fill all fields');
    try {
      const appointment = appointments.find(a => a.id === parseInt(updateId));
      if (!appointment) return alert('Appointment not found. Please search it first.');

      await axios.put(`https://hospital-management-backend-a52a.onrender.com/appointments/${updateId}`, {
        ...appointment,
        appointmentDate: updateDate,
        appointmentTime: updateTime
      });

      alert('Appointment updated');
      fetchAppointments();
    } catch (error) {
      console.error('Error updating appointment:', error);
    }
  };

  const handleDelete = async () => {
    if (!deleteId) return alert('Enter ID to delete');
    try {
      await axios.delete(`https://hospital-management-backend-a52a.onrender.com/appointments/${deleteId}`);
      alert('Appointment deleted');
      fetchAppointments();
    } catch (error) {
      console.error('Error deleting appointment:', error);
    }
  };

  const handleMarkVisited = async (id) => {
    try {
      await axios.patch(`https://hospital-management-backend-a52a.onrender.com/appointments/${id}/visited?visited=true`);
      fetchAppointments();
    } catch (error) {
      console.error('Error marking visited:', error);
      alert('Failed to update visited status.');
    }
  };

  const handleApprove = async (id) => {
    try {
      await axios.patch(`https://hospital-management-backend-a52a.onrender.com/appointments/${id}/status?status=APPROVED`);
      fetchAppointments();
    } catch (error) {
      console.error('Error approving appointment:', error);
    }
  };

  const handleReject = async (id) => {
    const reason = prompt('Enter rejection reason:');
    if (!reason) return;
    try {
      await axios.patch(`https://hospital-management-backend-a52a.onrender.com/appointments/${id}/reject?rejectionReason=${encodeURIComponent(reason)}`);
      fetchAppointments();
    } catch (error) {
      console.error('Error rejecting appointment:', error);
    }
  };

  const handleSubmitReview = async (id) => {
    const review = reviewInputs[id];
    if (!review?.weekLabel || !review?.notes) {
      return alert('Please enter both week label and notes');
    }

    try {
      await axios.post(`https://hospital-management-backend-a52a.onrender.com/appointments/${id}/reviews`, {
        weekLabel: review.weekLabel,
        notes: review.notes
      });

      alert('Review submitted');
      setReviewInputs((prev) => ({ ...prev, [id]: { weekLabel: '', notes: '' } }));
      fetchAppointments();
    } catch (error) {
      console.error('Error submitting review:', error);
      alert('Failed to submit review');
    }
  };

  const totalPages = Math.ceil(appointments.length / rowsPerPage);
  const paginatedAppointments = Array.isArray(appointments)
    ? appointments.slice((currentPage - 1) * rowsPerPage, currentPage * rowsPerPage)
    : [];

  const handlePageChange = (page) => {
    if (page >= 1 && page <= totalPages) setCurrentPage(page);
  };

  return (
    <div className="appointment-list">
      <h2>Appointment List</h2>

      <div className="search-bar">
        <input
          type="text"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          placeholder="Search by Patient Name"
        />
        <button onClick={handleSearch}><FaSearch /></button>
      </div>

      <div className="update-section">
        <input
          type="text"
          placeholder="Update ID"
          value={updateId}
          onChange={(e) => setUpdateId(e.target.value)}
        />
        <input
          type="date"
          value={updateDate}
          onChange={(e) => setUpdateDate(e.target.value)}
        />
        <input
          type="time"
          value={updateTime}
          onChange={(e) => setUpdateTime(e.target.value)}
        />
        <button className="update-btn" onClick={handleUpdateTop}>Update</button>
      </div>

      <div className="delete-section">
        <input
          type="text"
          placeholder="Delete ID"
          value={deleteId}
          onChange={(e) => setDeleteId(e.target.value)}
        />
        <button className="delete-btn" onClick={handleDelete}>Delete</button>
      </div>

      <table className="appointment-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Patient Name</th>
            <th>DOB</th>
            <th>Date</th>
            <th>Time</th>
            <th>Status</th>
            <th>Review</th>
            <th>Visited</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {paginatedAppointments.map((appt) => (
            <tr key={appt.id}>
              <td>{appt.id}</td>
              <td>{appt.patientName}</td>
              <td>{appt.patientDob}</td>
              <td>{appt.appointmentDate}</td>
              <td>{appt.appointmentTime}</td>
              <td>{appt.status}</td>
              <td>
                {appt.review ? (
                  <>
                    <strong>{appt.review.weekLabel}</strong><br />
                    <em>{appt.review.notes}</em>
                  </>
                ) : appt.status === 'APPROVED' && appt.visited ? (
                  <div style={{ display: 'flex', flexDirection: 'column', gap: '4px' }}>
                    <input
                      type="text"
                      placeholder="Week label"
                      value={reviewInputs[appt.id]?.weekLabel || ''}
                      onChange={(e) =>
                        setReviewInputs((prev) => ({
                          ...prev,
                          [appt.id]: {
                            ...prev[appt.id],
                            weekLabel: e.target.value,
                            notes: prev[appt.id]?.notes || '',
                          },
                        }))
                      }
                    />
                    <textarea
                      rows="2"
                      placeholder="Notes"
                      value={reviewInputs[appt.id]?.notes || ''}
                      onChange={(e) =>
                        setReviewInputs((prev) => ({
                          ...prev,
                          [appt.id]: {
                            ...prev[appt.id],
                            notes: e.target.value,
                            weekLabel: prev[appt.id]?.weekLabel || '',
                          },
                        }))
                      }
                    />
                    <button onClick={() => handleSubmitReview(appt.id)}>Submit</button>
                  </div>
                ) : (
                  'N/A'
                )}
              </td>
              <td>{appt.visited ? 'Yes' : 'No'}</td>
              <td>
                {appt.status === 'PENDING' && (
                  <>
                    <button onClick={() => handleApprove(appt.id)}>Approve</button>{' '}
                    <button onClick={() => handleReject(appt.id)}>Reject</button>
                  </>
                )}
                {appt.status === 'APPROVED' && !appt.visited && (
                  <button onClick={() => handleMarkVisited(appt.id)}>Mark Visited</button>
                )}
                {appt.status === 'APPROVED' && appt.visited && <span>Visited</span>}
                {appt.status === 'REJECTED' && (
                  <span style={{ color: 'red' }}>{appt.rejectionReason || 'Rejected'}</span>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {totalPages > 1 && (
        <div className="pagination">
          <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
            <IoPlaySkipBack />
          </button>
          {Array.from({ length: totalPages }, (_, i) => (
            <button
              key={i + 1}
              className={currentPage === i + 1 ? 'active' : ''}
              onClick={() => handlePageChange(i + 1)}
            >
              {i + 1}
            </button>
          ))}
          <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages}>
            <IoPlaySkipForward />
          </button>
        </div>
      )}
    </div>
  );
};

export default AppointmentListPage;
