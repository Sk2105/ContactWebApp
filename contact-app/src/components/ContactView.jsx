import { useEffect, useRef, useState } from 'react'
import { Link, useParams } from 'react-router-dom'


export default function ContactView() {
    const [contact, setContact] = useState({});
    const { id } = useParams();
    const fileInputRef = useRef(null);
    const [image, setImage] = useState(null);
    const [imagePreview, setImagePreview] = useState(null);

    const handleButtonClick = () => fileInputRef.current.click();

    const handleFileChange = (event) => {
        const selectedFile = event.target.files[0];
        setImage(selectedFile);

        if (selectedFile) {
            const reader = new FileReader();
            reader.onload = () => setImagePreview(reader.result);
            reader.readAsDataURL(selectedFile);
        }
    };

    useEffect(() => {
        if (image) {
            const formData = new FormData();
            formData.append("image", image);
            fetch(`http://localhost:8080/contact/${id}/profile`, {
                method: "POST",
                body: formData
            })
                .then((res) => res.text())
                .then(() => fetchContact())
                .catch((err) => console.error(err));
        }
    }, [image]);

    useEffect(() => {
        fetchContact();
    }, []);

    const fetchContact = () => {
        fetch(`http://localhost:8080/contact/${id}`)
            .then((res) => res.json())
            .then((data) => setContact(data));
    };

    const handleDelete = () => {
        fetch(`http://localhost:8080/contact/${id}`, {
            method: "DELETE"
        })
            .then((res) => res.text())
            .then(() => (window.location.href = "/"))
            .catch((err) => console.error(err));
    };

    return (
        <div className="w-full h-screen flex bg-sky-200 justify-center items-center">
            <div className="w-1/2 h-fit bg-white rounded-xl flex justify-center items-center flex-col shadow-2xl p-6">
                <div className="w-fit flex justify-center items-center">
                    <img
                        src={
                            contact.imageUrl == null
                                ? "https://th.bing.com/th/id/OIP.biOhQbUvYNusZQSnqK__jgHaF6?rs=1&pid=ImgDetMain"
                                : contact.imageUrl
                        }
                        className="w-[100px] border border-gray-600 h-[100px] rounded-full object-cover"
                    />

                    <input
                        type="file"
                        accept="image/png"
                        style={{ display: "none" }}
                        ref={fileInputRef}
                        onChange={handleFileChange}
                    />
                    <button
                        onClick={handleButtonClick}
                        className="w-fit relative shadow-2xl shadow-black bg-white border border-black end-5 top-5 h-fit p-2 hover:bg-gray-400 text-sm text-white rounded-full"
                    >
                        ✏️
                    </button>
                </div>

                <h1 className="w-full text-center p-2 text-xl font-bold">
                    {" "}
                    {contact.name}
                </h1>
                <p className="w-full text-center text-lg">
                    {" "} {contact.email}
                </p>
                <p className="w-full text-center text-lg">
                    {" "} {contact.phone}
                </p>
                <div className="w-full flex m-2 p-2 justify-around items-center">
                    <Link to={`/edit/${id}`}>
                        <button
                            onClick={() => {}}
                            className="w-1/3 bg-sky-600 p-2 hover:bg-sky-900  text-white rounded-md"
                        >
                            Edit
                        </button>
                    </Link>
                    <button
                        onClick={handleDelete}
                        className="w-1/3 bg-red-600 p-2 hover:bg-red-900  text-white rounded-md"
                    >
                        Delete
                    </button>
                </div>
            </div>
        </div>
    );
}
